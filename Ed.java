// (c) Travis Dowd
// (d) 7-1-2020
//
// A simple text editor
//

package tedi;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.io.*;
import java.util.Scanner;
import javafx.scene.control.TextAreaBuilder;
import javafx.scene.control.TextFormatter.Change;

public class Ed extends Application {
    // -------------
    //   Variables
    // -------------
    // System
    private File file;
    private String os = System.getProperty( "os.name" ); // May not be needed
    private String dir = System.getProperty( "user.dir" );
    private String filePath;
    // JavaFX
    TextArea input = new TextArea();
    // Key combinations
    public KeyCombination ctrlX = new KeyCodeCombination( KeyCode.X, KeyCombination.CONTROL_DOWN );
    public KeyCombination ctrlO = new KeyCodeCombination( KeyCode.O, KeyCombination.CONTROL_DOWN );
    public KeyCombination ctrlS = new KeyCodeCombination( KeyCode.S, KeyCombination.CONTROL_DOWN );
    public KeyCombination ctrlF = new KeyCodeCombination( KeyCode.F, KeyCombination.CONTROL_DOWN );
    public KeyCombination ctrlB = new KeyCodeCombination( KeyCode.B, KeyCombination.CONTROL_DOWN );

    @Override
    public void start( Stage primaryStage ) throws Exception {
        // ---------
        //   Input
        //----------
        input.setEditable( true );
        
        // ------------
        //  Mode Line
        // ------------
        TextArea mode = TextAreaBuilder.create()
            .prefWidth( 640 )
            .prefHeight( 3 )
            .wrapText( false )
            .build();
        mode.setText("> ");
        mode.setEditable( false );
        Shell shell = new Shell( mode );
        PrintStream ps = new PrintStream( shell, true );
        System.setOut( ps );
        System.setErr( ps );
        
        // ----------
        //    Init
        // ----------
        BorderPane pane = new BorderPane();
        pane.setCenter( input );
        pane.setBottom( mode );
        Scene scene = new Scene( pane, 640, 480 );
        scene.getStylesheets().add( getClass().getResource( "style.css" ).toExternalForm());
        primaryStage.setTitle( "tedi" );
        primaryStage.setScene( scene );
        primaryStage.show();
        input.requestFocus();
        
        // ------------
        //   Controls
        // ------------
        //
        // Control of keyboard shortcuts
        //
        // Input TextArea
        input.setOnKeyPressed( new EventHandler<KeyEvent>() {
            @Override
            public void handle( KeyEvent event ) {
                if ( ctrlX.match( event )) { // Switch to mode line
                    mode.requestFocus();
                    //mode.positionCaret( mode.getCaretPosition() + 3 );
                    mode.clear();
                    mode.setEditable( true );
                    input.setEditable( false );
                }  // Open file
                if ( ctrlO.match( event )) {
                    FileChooser openFileChooser = new FileChooser();
                    openFileChooser.setInitialDirectory( new File( dir )); // Need to test on windows
                    file = openFileChooser.showOpenDialog( primaryStage );
                    input.clear();
                    if ( file != null ){
                        filePath = file.getAbsolutePath();
                        Stage primaryStage = ( Stage ) pane.getScene().getWindow();
                        writeToInput();
                    } // Save file
                }  if ( ctrlS.match( event )) {
                    FileChooser saveFileChooser = new FileChooser();
                    saveFileChooser.setInitialDirectory( new File( dir )); // Need to test on windows
                    file = saveFileChooser.showSaveDialog( primaryStage );
                    try {
                        filePath = file.getAbsolutePath();
                        Stage primaryStage = ( Stage ) pane.getScene().getWindow();
                        BufferedWriter writer = new BufferedWriter( new FileWriter( file ));
                        PrintWriter output = new PrintWriter( writer );
                        output.write( input.getText() );
                        output.flush();
                        output.close();
                    } catch ( IOException e ) {
                        System.out.println( "[-]Error: " + file.getName() );
                    } // Move Forward
                } if ( ctrlF.match( event )) {
                    input.positionCaret( input.getCaretPosition() + 1 );
                } // Move Backwards
                if ( ctrlB.match( event )) {
                    input.positionCaret( input.getCaretPosition() - 1 );
                }
            }
        }); // Mode TextArea
        mode.setOnKeyPressed( new EventHandler<KeyEvent>() {
            @Override
            public void handle( KeyEvent event ) {
                KeyCode kc = event.getCode();
                if ( ctrlX.match( event )) {
                    //mode.clear();
                    mode.setText( "> " );
                    input.requestFocus();
                    mode.setEditable( false );
                    input.setEditable( true );
                } if ( kc.equals( KeyCode.ENTER )) {
                    String str = mode.getText();
                    String[] cmd = str.split(" ");
                    shell.run( cmd );
                }
            }
        });
    }
    
    // -----------------
    //   Write to file
    // -----------------
    //
    // Write text from input TextArea to file
    //
    public void writeToInput() {
        try {
            Scanner reader = new Scanner( file );
            while ( reader.hasNextLine() ) {
                String current = reader.nextLine();
                input.appendText( current + "\n" );
            } reader.close();
        } catch ( FileNotFoundException e ) {
            System.out.println( "Could not read file: " + file.getName() );
        }
    }
    
    // --------
    //   Main
    // --------
    public static void main( String[] args ) {
        launch( args );
    }
}
