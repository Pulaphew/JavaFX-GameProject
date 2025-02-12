package pane;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class GameBattlePane extends AnchorPane {

	PlayerPane playerPane;
	EnemyPane enemyPane;
	GameMenuBattlePane gameMenuBattlePane;

	public GameBattlePane(PlayerPane playerPane, EnemyPane enemyPane, GameMenuBattlePane gameMenuBattlePane) {
		this.playerPane = playerPane;
		this.enemyPane = enemyPane;
		this.gameMenuBattlePane = gameMenuBattlePane;
		//
		this.setPrefSize(1360, 768);
		// pane for background
		Pane backgroundPane = new Pane();
		backgroundPane.setPrefSize(1360, 768);
		ImageView backgroundImage = new ImageView();
		backgroundImage.setFitWidth(1360);
		backgroundImage.setFitHeight(768);
		// edit bg image from here
		String image_path = ClassLoader.getSystemResource("croissantTestImage.jpg").toString();
		backgroundImage.setImage(new Image(image_path));
		backgroundPane.getChildren().add(backgroundImage);

		// make surrender button
		Button surrenderButton = new Button("Surrender");
		surrenderButton.setPrefSize(175, 70);
		surrenderButton.setLayoutX(20);
		surrenderButton.setLayoutY(15);
		surrenderButton.setFont(new Font(30));
		
		//add all node into game battle pane
		this.getChildren().addAll(
				backgroundPane,
				surrenderButton,
				gameMenuBattlePane,
				playerPane,
				enemyPane);
	}

}
