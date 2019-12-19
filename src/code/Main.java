package code;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    double xOffset, yOffset;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent rootLayout = FXMLLoader.load(getClass().getResource(Const.ROOT_LAYOUT));
        Scene mainScene = new Scene(rootLayout, Const.SCENE_WIDTH, Const.SCENE_HEIGHT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        mainScene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(mainScene);
        primaryStage.setResizable(false);
        primaryStage.show();
        mkStageDraggable(primaryStage, rootLayout);
    }

    // Enable mouse dragging feature throughout the stage
    private void mkStageDraggable(Stage stage, Parent layout) {
        layout.setOnMousePressed(mouseEvent -> {
            xOffset = mouseEvent.getSceneX();
            yOffset = mouseEvent.getSceneY();
        });
        layout.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() - xOffset);
            stage.setY(mouseEvent.getScreenY() - yOffset);
        });
    }
}
