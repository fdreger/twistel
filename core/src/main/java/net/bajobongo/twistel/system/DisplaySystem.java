package net.bajobongo.twistel.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import net.bajobongo.twistel.assets.AssetsService;
import net.bajobongo.twistel.component.*;
import net.bajobongo.twistel.game.GameStateService;
import net.bajobongo.twistel.game.PlayerInput;
import net.bajobongo.twistel.infrastructure.GameCamera;
import net.bajobongo.twistel.infrastructure.GameSpriteBatch;
import net.snowyhollows.bento.annotation.WithFactory;

import java.util.HashSet;
import java.util.Set;

public class DisplaySystem extends IteratingSystem {

    public static final int RADIUS = 41;
    private final OrthographicCamera camera;
    private final GameSpriteBatch batch;
    private final GameStateService gameStateService;
    private final AssetsService assetsService;
    private final PlayerInput playerInput;

    @WithFactory
    public DisplaySystem(GameCamera camera, GameSpriteBatch batch, AssetsService assetsService, GameStateService gameStateService, PlayerInput playerInput) {
        super(Family.all(RectangleComponent.class).get());
        this.camera = camera;
        this.assetsService = assetsService;
        this.batch = batch;
        this.gameStateService = gameStateService;
        this.playerInput = playerInput;
    }

    private final Vector2 tmp = new Vector2();

    Color tmpColor = new Color();

    Set<Entity> popOver = new HashSet<>();

    @Override
    public void update(float deltaTime) {
        popOver.clear();

        batch.begin();
        batch.enableBlending();
        float bonusRatio = gameStateService.getBonusRatio();
        float width = bonusRatio * assetsService.getCircle().getRegionWidth() * 2f + 10f;
        float height = bonusRatio * assetsService.getCircle().getRegionHeight() * 2f + 10f;
        tmpColor.set(Color.WHITE);
        tmpColor.a = bonusRatio + 0.1f;
        batch.setColor(tmpColor);
        batch.drawCentered(assetsService.getCircle(), 400, 300, width, height);

        if (gameStateService.isPlaying()) {
            playerInput.gatherHoveredPlaces(popOver);
            for (Entity entity : popOver) {
                RectangleComponent rectangleComponent = entity.getComponent(RectangleComponent.class);
                tmpColor.a = 0.5f;
                batch.setColor(tmpColor);
                batch.drawCentered(assetsService.getCircle(), rectangleComponent.getCenterX(), rectangleComponent.getCenterY(), RADIUS, RADIUS);
            }
        }

        for (Entity entity : getEntities()) {
            Place place = entity.getComponent(Place.class);
            RectangleComponent rectangleComponent = entity.getComponent(RectangleComponent.class);
            if (place != null) {
                if (gameStateService.isPlaying() || true) {
                    if (entity.getComponent(Clickable.class) != null) {
                        tmpColor.set(Color.WHITE);
                    } else {
                        tmpColor.set(Color.BLACK);
                    }
                    tmpColor.a = 0.4f;
                    batch.setColor(tmpColor);
                    batch.drawCentered(assetsService.getCircle(), rectangleComponent.getCenterX(), rectangleComponent.getCenterY(), RADIUS, RADIUS);
                }
            } else {
                Alpha alpha = entity.getComponent(Alpha.class);
                float alphaValue = alpha == null ? 0.4f : alpha.value;
                tmpColor.set(entity.getComponent(Element.class).type.color);
                tmpColor.a = alphaValue;
                batch.setColor(tmpColor);
                batch.drawCentered(assetsService.getCircle(), rectangleComponent.getCenterX(), rectangleComponent.getCenterY(), rectangleComponent.getWidth(), rectangleComponent.getHeight());
            }
        }


        batch.end();

    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        // intentionally left blank, the action is in the update method
    }
}
