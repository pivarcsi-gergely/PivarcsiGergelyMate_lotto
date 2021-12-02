package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.*;

;

public class Controller {
    @FXML
    public Button gomb;
    @FXML
    public Label kihuzottSzam;
    @FXML
    public Label elso;
    @FXML
    public Label masodik;
    @FXML
    public Label harmadik;
    @FXML
    public Label negyedik;
    @FXML
    public Label otodik;
    @FXML
    public ArrayList<Integer> sorsoltSzamokLista = new ArrayList<>();

    private Random random;
    private boolean sorsolBef;
    private boolean rendezBef;
    private int kihuzottSzamSzam;
    private int gombLenyomasCount;

    @FXML
    public void initialize() {
        random = new Random();
        sorsolBef = false;
        rendezBef = false;
    }

    public void sorsolRendezCLick(ActionEvent actionEvent) {
        if (sorsolBef){
            if (rendezBef){
                reset();
            }
            else{
                rendez();
            }
        }
        else {
            sorsol();
        }
    }

    public void rendez(){
        gomb.setText("Sorsol");
        sorsoltSzamokLista.sort(Comparator.naturalOrder());
        for (int i = 0; i < 5; i++) {
            kiiras(i, Integer.toString(sorsoltSzamokLista.get(i)));
        }
        int listaIndex = random.nextInt(5);
        kihuzottSzam.setText(String.valueOf(sorsoltSzamokLista.get(listaIndex)));
        rendezBef = true;
    }

    public void reset() {
        sorsoltSzamokLista = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            kiiras(i, "");
        }
        gombLenyomasCount = 0;
        gomb.setText("Sorsol");
        kihuzottSzam.setText("0");
        rendezBef = false;
        sorsolBef = false;
    }

    public void sorsol() {

        Timer timerKihuzottAnimacio = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> kihuzottSzam.setText(Integer.toString(random.nextInt(90)+1)));
            }
        };
        timerKihuzottAnimacio.scheduleAtFixedRate(timerTask, 0, 1);
        Timer timer2 = new Timer();
        TimerTask timerTask2 = new TimerTask() {
            @Override
            public void run() {
                timerKihuzottAnimacio.cancel();
                Platform.runLater(() -> {
                    while (sorsoltSzamokLista.contains(kihuzottSzamSzam) || kihuzottSzamSzam == 0) {
                        kihuzottSzamSzam = random.nextInt(90) + 1;
                    }
                    String kihuzottSzamString = Integer.toString(kihuzottSzamSzam);
                    kihuzottSzam.setText(kihuzottSzamString);
                    kiiras(kihuzottSzamSzam);
                    if (gombLenyomasCount == 5) {
                        gomb.setText("Rendez");
                        sorsolBef = true;
                    }
                });
            }
        };
        timer2.schedule(timerTask2, 1000);
    }

   public void kiiras(int labelekSzama, String kihuzottString) {
        switch (labelekSzama){
            case 0: elso.setText(kihuzottString); break;
            case 1: masodik.setText(kihuzottString); break;
            case 2: harmadik.setText(kihuzottString); break;
            case 3: negyedik.setText(kihuzottString); break;
            case 4: otodik.setText(kihuzottString); break;
        }
    }

    public void kiiras(int lottoszam) {
        sorsoltSzamokLista.add(lottoszam);
        kiiras(gombLenyomasCount, Integer.toString(lottoszam));
        gombLenyomasCount++;
    }
}
