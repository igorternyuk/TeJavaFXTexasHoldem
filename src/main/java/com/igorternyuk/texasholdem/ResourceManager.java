package com.igorternyuk.texasholdem;

import com.igorternyuk.texasholdem.model.Card;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by igor on 18.03.18.
 */
public class ResourceManager {
    private static ResourceManager instance = null;
    private static final String PATH_TO_CARD_SPRITE_SET = "img/CardsSprite.png";
    private static final String PATH_TO_CARD_BACK_SIDE = "img/cardBack.png";
    private static final String SPRITE_LOADING_ERROR_MESSAGE = "Could not load card sprite set";
    public static final int CARD_WIDTH = 73;
    public static final int CARD_HEIGHT = 98;
    private BufferedImage spriteSet = null;
    private BufferedImage[][] cardImages = null;
    private BufferedImage cardBackSideImage = null;

    private ResourceManager() {
        try {
            this.spriteSet = ImageIO.read(getClass().getClassLoader().getResource(PATH_TO_CARD_SPRITE_SET));
            this.cardBackSideImage = ImageIO.read(getClass().getClassLoader().getResource(PATH_TO_CARD_BACK_SIDE));
            this.cardImages = createCardImages();
        } catch (IOException ex) {
            System.out.println(SPRITE_LOADING_ERROR_MESSAGE);
            System.out.println(ex.getMessage());
        }
    }

    public Image getCardImage(final Card card) {
        BufferedImage img = cardImages[card.getSuit().ordinal()][getPositionXOnSpriteByCard(card)];
        return SwingFXUtils.toFXImage(img, null);
    }

    public Image getCardBack() {
        return SwingFXUtils.toFXImage(this.cardBackSideImage, null);
    }

    private BufferedImage[][] createCardImages() {
        final int numberOfSuits = Card.Suit.values().length;
        final int numberOfRanks = Card.Rank.values().length;
        final BufferedImage[][] cardImages = new BufferedImage[numberOfSuits][numberOfRanks];
        for (int y = 0; y < numberOfSuits; ++y) {
            for (int x = 0; x < numberOfRanks; ++x) {
                cardImages[y][x] = this.spriteSet.getSubimage(x * CARD_WIDTH, y * CARD_HEIGHT,
                        CARD_WIDTH + 1, CARD_HEIGHT + 1);
            }
        }
        return cardImages;
    }

    public int getPositionXOnSpriteByCard(final Card card) {
        if (card.getRank().isFigure()) {
            switch (card.getRank()) {
                case JACK:
                    return 10;
                case QUEEN:
                    return 11;
                case KING:
                    return 12;
                default:
                    return 0;
            }
        } else {
            return card.getValue() - 1;
        }
    }

    public static synchronized ResourceManager getInstance() {
        if (instance == null) {
            instance = new ResourceManager();
        }
        return instance;
    }

    public void dispose() {
        instance = null;
    }

}