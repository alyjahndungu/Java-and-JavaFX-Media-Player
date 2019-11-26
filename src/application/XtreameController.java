package application;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.media.MediaPlayer.Status;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.util.Duration;

public class XtreameController extends AnchorPane {
	@FXML
	MenuItem mtOpen,mtQuit ,mtPlaylist,mtLibrary;
	@FXML
	MediaView mediaView;
	@FXML
	ImageView imgSong;
	@FXML
	Slider musicTimer, volSlider;
	@FXML
	Button butBack,butPlay,butNext,butRewind,butForward,btnPlaylist,btnClearList;
	@FXML
	Label trackTitle,fullTimeLabel;
	@FXML
	ImageView albumCover, imageAlbum;
	@FXML
	public ListView<File> 	playlistView;

   Window main;
	Media media;
  MediaPlayer Mplay;
Duration duration;
private ObservableList<File> playListFiles =FXCollections.observableArrayList();
private ObjectProperty<Path> selectedMedia = new SimpleObjectProperty<>();
private ObjectProperty<Path> deletedMedia = new SimpleObjectProperty<>();
FileChooser 	fileChooser = new FileChooser();
	public void OpenFile(){
			mtOpen.setOnAction(new EventHandler<ActionEvent>(){

				@Override
				public void handle(ActionEvent event){
					// Inorder to jump to the certain part of video

					musicTimer.valueProperty().addListener(new InvalidationListener() {
						public void invalidated(Observable ov){
							if (musicTimer.isPickOnBounds()) { // It would set the time
								Mplay.seek(Mplay.getMedia().getDuration().multiply(musicTimer.getValue() / 100));

								// providing functionality to volume slider
								volSlider.valueProperty().addListener(new InvalidationListener() {
									public void invalidated(Observable ov)
									{
										if (volSlider.isPressed()) {

											Mplay.setVolume(volSlider.getValue() /100); // It would set the volume
										}else{
											volSlider.setValue(0);
										}
									}
								});

							}
						}
					});
						File file = fileChooser.showOpenDialog(main);
						if (file != null) {
							String path = file.toURI().toString();
							fileChooser.getExtensionFilters().addAll(
		        	                new FileChooser.ExtensionFilter("Files",
		        	                        PropertiesUtils.readFormats()));
                                        if(Mplay != null){
                                        	 Mplay.stop(); //Stops the currently playing music

                                        }
              		Media me=new Media(path);
              	  path=path.replace("\\", "/");
                         		Mplay=new MediaPlayer(me);
                          		mediaView.setMediaPlayer(Mplay);
                          		final DoubleProperty width = mediaView.fitWidthProperty();
                          		final DoubleProperty height = mediaView.fitHeightProperty();
                          		width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
                          		height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
                          		mediaView.setPreserveRatio(true);
                          		mediaView.setSmooth(true);
                          	mediaView.setFocusTraversable(true);
                          		mediaView.getMediaPlayer();
                          		 Mplay.setAutoPlay(true);
                         		 Mplay.play();

						 }
						 Mplay.setAutoPlay(true);

						fullTimeLabel.textProperty().bind(
						Bindings.createStringBinding(() -> {
						Duration time = Mplay.getCurrentTime();
						return String.format("%4d:%02d:%04.1f",
						(int) time.toHours(),
						(int) time.toMinutes() % 60,
						time.toSeconds() % 3600);
						},
						Mplay.currentTimeProperty()));


				//to double click the mediaview to pause and play
			            mediaView.addEventFilter(MouseEvent.MOUSE_PRESSED, (mouseEvent) -> {
			                if (mouseEvent.getButton().equals(
			                        MouseButton.PRIMARY)) {
			                    if (mouseEvent.getClickCount() == 2) {
			                        Mplay.pause();
                                    try {
                                        File fileImage = new File("src/application/play.png");
                                        Image image = new Image(fileImage.toURI().toString());
                                        albumCover.setImage(image);
										Thread.sleep(500);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

			                        } else {
			                        	   Mplay.play();
                                        try {
                                            File fileImage = new File("src/application/pauseImg.png");
                                            Image image = new Image(fileImage.toURI().toString());
                                            albumCover.setImage(image);
											Thread.sleep(500);
										} catch (InterruptedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}

			                        }
			                    }

			            });

				}

			});


			}



	public void quitProgram(){
		mtQuit.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				// Close Xtreame BeatBox
				Alert alert=new Alert(AlertType.CONFIRMATION);
                 alert.setHeaderText("Exiting Xtreame BeatBox");
                 alert.setTitle("Xtreame BeatBox");
                 String s="Are you sure you want to close the Application";
                 alert.setContentText(s);
             Optional<ButtonType> result=alert.showAndWait();
                   if(result.isPresent() && (result.get()==ButtonType.OK)) {
                	   System.exit(1);

                   }


			}

		});
	}



    public ObservableList<File>  listViewItems(){
        return playListFiles;
     }

     public ObjectProperty<Path> selectedFile(){
         return selectedMedia;
     }

     public ObjectProperty<Path> deletedFile() {
         return deletedMedia;
     }

	public void back(){
		butBack.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				Mplay.seek(Mplay.getStartTime());
				Mplay.stop();



			}

		});
	}
	public void play(){;
	butPlay.getStyleClass().add("myButton");

		butPlay.setOnAction(new EventHandler<ActionEvent>(){


			public void handle(ActionEvent event){

				Status status = Mplay.getStatus();
					if (status == Status.PLAYING) {
						// If the status is Video playing
						if (Mplay.getCurrentTime().greaterThanOrEqualTo(Mplay.getTotalDuration())) {

							Mplay.seek(Mplay.getStartTime());
							Mplay.play();
						}
						else {

							Mplay.pause();
						butPlay.setText(">");
						}
					}
					if (status == Status.HALTED || status == Status.STOPPED || status == Status.PAUSED) {
						Mplay.play(); // Start the video
					butPlay.setText("||");
					}

			   }});

	    //providing functionality to time slider
	     Mplay.currentTimeProperty().addListener(new InvalidationListener(){
	    	  public void invalidated(Observable ov)
              {
                  updatesValues();
              }
	     });


	}
	//outside constructer
	private void updatesValues() {
		if(fullTimeLabel != null && musicTimer!=null && volSlider!=null){
         Platform.runLater(() ->{
        	 Duration currentTime =Mplay.getCurrentTime();

        	 musicTimer.setDisable(duration.isUnknown());
			if (!musicTimer.isDisabled() && duration.greaterThan(Duration.ZERO) && musicTimer.isValueChanging()) {
				               musicTimer.valueProperty().setValue(currentTime.divide(duration.toMillis()).toMillis() * 100);
								}

								if (!volSlider.isValueChanging()) {
								volSlider.setValue((int)Math.round(Mplay.getVolume() / 100));
								}




							});
	}
	            }




	public void next(){
		butNext.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				Mplay.seek(Mplay.getCurrentTime());
				Mplay.setAutoPlay(true);
				Mplay.stop();


			}

		});
	}

	public void rewindBack(){
		butRewind.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				Mplay.seek(Mplay.getCurrentTime().divide(1.5));
				Mplay.setAutoPlay(true);

			}
		});
	}
	public void forward(){
		butForward.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				Mplay.seek(Mplay.getCurrentTime().multiply(1.5));


			}

		});
	}




		}
