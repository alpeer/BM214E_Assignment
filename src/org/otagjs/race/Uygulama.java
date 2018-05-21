package org.otagjs.race;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Uygulama extends Frame implements WindowListener {
    public  JPanel APPView;
    private JPanel Yaris;
    private JButton Bastan;
    private JPanel Yaris2;
    private JTextField Ad2;
    private JTextField Ad;
    private JButton At2;
    private JButton At;
    private JLabel Puan;
    private JLabel Puan2;
    private JLabel zar1Label;
    private JLabel zar2Label;
    private JLabel İleti;
    private JPanel[] Track;
    private JPanel[] Track2;
    private int[] Progress= {0,0};
    private int uzunluk=0;
    private boolean sıra,sonSıra=true;
    private int[] Puanlar={0,0};
    private int[][] sonZar={{0,0},{0,0}};

    public Uygulama() {
        this.Track= new JPanel[50];
        this.Track2= new JPanel[50];
        addWindowListener(this);
        Bastan.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
               Uygulama:Sıfırla();
            }
        });
        JPanel panel,panel2;
        // Yarış Alanını kur
        this.Yaris.setLayout(new FlowLayout());
        for (int i=0;i<50;i++){
            panel=this.Track[i]=new JPanel();
            panel2=this.Track2[i]=new JPanel();
            this.Yaris.add(panel);
            this.Yaris2.add(panel2);
            panel.setSize(50,50);
            panel2.setSize(50,50);
            panel.setBackground(Color.GRAY);
            panel2.setBackground(Color.GRAY);
        }
        At.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(At.isEnabled()){
                    Uygulama:ZarAt(0);
                }

            }
        });
        At2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(At2.isEnabled()){
                    Uygulama:ZarAt(1);
                }
            }
        });
        this.Sıfırla();
        this.APPView.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyCode()==KeyEvent.VK_UP&&At2.isEnabled()){
                    Uygulama:ZarAt(1);
                }
                if(e.getKeyCode()==87&&At.isEnabled()){
                    Uygulama:ZarAt(0);
                }
            }
        });
    }
    private void Sıfırla(){
        //Süreci Sıfırla
        this.Progress[0]=0;
        this.Progress[1]=0;

        // İletişim Kutusu Zarları 0la
        this.İleti.setText("");
        zar1Label.setText("");
        zar2Label.setText("");
        this.sonZar[0][0]=0;
        this.sonZar[1][0]=0;
        this.sonZar[0][1]=0;
        this.sonZar[1][1]=0;

        //Son sıraya göre sıranın kimde olduğunu belirle
        this.sonSıra=!this.sonSıra;

        // Sıraya göre Zarı teslim et
        this.zarDevret(this.sonSıra);

        // 20-50 arasında süreç uzunluğu seç
        int random = this.uzunluk=20+((int)(Math.random()*1000)%30);
        System.out.println(random);

        // süreç uzunluğuna göre yarış pistini etkinleştir
        for (int i=0;i<50;i++){
            this.Track[i].setVisible(i<random);
            this.Track2[i].setVisible(i<random);
            this.Track[i].setBackground(Color.GRAY);
            this.Track2[i].setBackground(Color.GRAY);
        }
    }
    private void ZarAt(int oyuncu){

        int yürü = 0;

        // Denkgele iki zar at
        int[] zar = {1 + ((int) (Math.random() * 1000) % 6), 1 + ((int) (Math.random() * 1000) % 6)};

        // zarları göster
        zar1Label.setText(zar[0] + "");
        zar2Label.setText(zar[1] + "");

        // Eğer oyuncunun bir önceki zarda aynısını attıysa Zar Tutmuştur
        if(
            (this.sonZar[oyuncu][0]==zar[0]&&this.sonZar[oyuncu][1]==zar[1])||
            (this.sonZar[oyuncu][0]==zar[1]&&this.sonZar[oyuncu][1]==zar[0])
                ){
            this.ZarTuttu(oyuncu);
        }else {
            //Zarların toplamı kadar ilerleyecek
            yürü = zar[0] + zar[1];

            // Çift attıysa ikiye katla
            if (zar[0] == zar[1]) {
                yürü *= 2;
            }

            //Oyuncunun son attığı zarı güncelle
            this.sonZar[oyuncu][0]=zar[0];
            this.sonZar[oyuncu][1]=zar[1];

            //Zarı diğer kişiye devret
            this.zarDevret(!this.sıra);
            this.Yürü(oyuncu, yürü);
        }
    }
    private void zarDevret(boolean sıra){
        this.sıra = sıra;
        this.At.setEnabled(!sıra);
        this.At2.setEnabled(sıra);
    }
    private void Yürü(int oyuncu,int move){
        int score=this.Progress[oyuncu];
        JPanel[] track=oyuncu>0?this.Track2:this.Track;
        for (int i=0,u=this.uzunluk;i<move&&(score+i<u);i++){
            track[score+i].setBackground(oyuncu==0?Color.BLUE:Color.pink);
        }

        this.Progress[oyuncu]+=move;
        // Tamamlanmışsa oyuncuya kazandır
        if(this.Progress[oyuncu]>=this.uzunluk){
            this.Win(oyuncu);
        }
    }
    private void Win(int oyuncu){
        this.At.setEnabled(false);
        this.At2.setEnabled(false);

        //Puan artır
        this.Puanlar[oyuncu]++;
        (oyuncu>0?this.Puan2:this.Puan).setText("Puan: "+this.Puanlar[oyuncu]);
    }
    private void ZarTuttu(int oyuncu){
        this.At.setEnabled(false);
        this.At2.setEnabled(false);

        //Puan azalt
        this.Puanlar[oyuncu]--;
        (oyuncu>0?this.Puan2:this.Puan).setText("Puan: "+this.Puanlar[oyuncu]);
        this.İleti.setText((oyuncu==0?this.Ad:this.Ad2).getText()+" zar tuttu, -1 ceza puanı");
    }
    @Override
    public void windowOpened(WindowEvent e) {

    }

    public void windowClosing(WindowEvent e) {
        dispose();
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}