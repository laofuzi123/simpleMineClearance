package lc.com;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**简易扫雷*/
public class SweepBySwing {
    private static final String SRC_URL = "/Users/mac/IdeaProjects/mineClearance/src/main/resources/";
    private static int[][] arr  = new int[15][15];
    private static int[][] book = new int[15][15];
    private static  int wipeCount = 0;//消灭的格子数量
    private static JButton[][] buttons = new JButton[15][15];
    private static JButton button;
    private static URL mus;
    /**生成扫雷的二维数组*/
    /*
     *  后续在更新鼠标的右键标记雷和鼠标双击事件
     *
     * */
    public static void paintMoRen(){

        for(int i=0;i<30;i++){
            Random ran = new Random();
            int lx = ran.nextInt(15);
            int ly = ran.nextInt(15);
            if(arr[lx][ly] == 0){//表示这个地方还没有布置雷
                arr[lx][ly] = -1;
            }else{
                i--;
            }
        }
        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr[i].length;j++){
                arr[i][j] = generationNumber(i,j);
            }
        }
    }
    public static  int generationNumber(int x ,int y){
        //定义一个雷数从零开始
        int res = 0;
        //首先应该判断这个点是不是属于雷区
        if(arr[x][y] != -1){
            //这里就应该判断八个方向是不是有雷 如果有雷则数值加1 按顺时针方向判断
            //向右时 x不变y+1
            if( x >=0 && x <arr.length && y+1 >= 0 && y+1 < arr.length && arr[x][y+1] == -1) res++;
            if( x+1 >=0 && x+1 <arr.length && y+1 >= 0 && y+1 < arr.length && arr[x+1][y+1] == -1) res++;
            if( x+1 >=0 && x+1 <arr.length && y >= 0 && y < arr.length && arr[x+1][y] == -1 ) res++;
            if( x+1 >=0 && x+1 <arr.length && y-1 >= 0 && y-1 < arr.length && arr[x+1][y-1] == -1 ) res++;
            if( x >=0 && x <arr.length && y-1 >= 0 && y-1 < arr.length && arr[x][y-1] == -1 ) res++;
            if( x-1 >=0 && x-1 <arr.length && y-1 >= 0 && y-1 < arr.length && arr[x-1][y-1] == -1 ) res++;
            if( x-1 >=0 && x-1 <arr.length && y >= 0 && y < arr.length && arr[x-1][y] == -1 ) res++;
            if( x-1 >=0 && x-1 <arr.length && y+1 >= 0 && y+1 <arr.length && arr[x-1][y+1] == -1 ) res++;
            return res;
        }else{
            return -1;
        }

    }
    /**如果周围没有雷全部显示*/
    //这里应该还要写一个 0 周围的所有八个格子都要显示
    private static  void showRound(int x,int y){
        book[x][y] = 1;
        int [][] next = {{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1},{-1,0},{-1,1}};
        for(int k=0;k<next.length;k++){
            int tx = x + next[k][0];
            int ty = y + next[k][1];
            if(tx >=0 && tx < arr.length && ty >=0 && ty < arr.length && book[tx][ty] == 0){
                book[tx][ty] = 1;
                wipeCount++;

                if(arr[tx][ty] == 0){
                    showRound(tx,ty);
                }
            }
        }
    }
    // 建立一个点击某个按钮 根据不同的按钮显示不同的界面
    public static void showViewByButton(int num,IndexJButton eventButton){

    }
    // 采用java的swing组件编写界面
    public  void produceView() throws Exception {
        JFrame jf = new JFrame("简易扫雷");
        JPanel jp = new JPanel();
        GridLayout gl = new GridLayout(15,15);//java.lang.Integer
        jp.setLayout(gl);
        //JPanel jp = new JPanel(new GridLayout(20,20,0,0));
        MouseListener ml = new MouseListener() {


            public void mouseReleased(MouseEvent e) {
            }


            public void mousePressed(MouseEvent e) {
            }


            public void mouseExited(MouseEvent e) {
            }


            public void mouseEntered(MouseEvent e) {
            }

            public void mouseClicked(MouseEvent e) {

                try {
                    mus = new File(SRC_URL+"7116.wav").toURI().toURL();
                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                }
                Applet.newAudioClip(mus).play();
                if(e.getButton() == 1){
                    // 表示鼠标左击 打开
                    IndexJButton eventButton = (IndexJButton) e.getSource();
                    int x = eventButton.x;
                    int y = eventButton.y;
                    if(arr[x][y] == 0){
                        wipeCount++;

                        showRound(x, y);
                        if(wipeCount == 185){
                            // 表示成功完成任务

                            try {
                                mus = new File(SRC_URL+"8745.wav").toURI().toURL();
                            } catch (MalformedURLException e1) {
                                e1.printStackTrace();
                            }
                            Applet.newAudioClip(mus).play();
                            JOptionPane.showMessageDialog
                                    (null, "你胜利了"+wipeCount+"个格子", "你胜利了", JOptionPane.ERROR_MESSAGE);

                        }
                        for(int m=0;m<book.length;m++){
                            for(int n=0;n<book[m].length;n++){
                                if(book[m][n] == 1){
                                    String str;
                                    if(arr[m][n] == 0){
                                        str = "";
                                    }else{
                                        str = arr[m][n]+"";
                                    }
                                    button = null;
                                    button = buttons[m][n];
                                    //button.setContentAreaFilled(false);
                                    //button.setEnabled(false);
                                    //button.setText(str);

                                    try {
                                        button.setIcon(new ImageIcon(new File(SRC_URL+"11"+arr[m][n]+".jpg").toURI().toURL()));
                                    } catch (MalformedURLException e1) {
                                        e1.printStackTrace();
                                    }
                                    //System.out.println(arr[x][y]);
                                }
                            }
                        }
                    }else if(arr[x][y] > 0){
                        wipeCount++;
                        if(wipeCount == 185){
                            // 表示成功完成任务
                            try {
                                mus = new File(SRC_URL+"8745.wav").toURI().toURL();
                            } catch (MalformedURLException e1) {
                                e1.printStackTrace();
                            }
                            Applet.newAudioClip(mus).play();
                            JOptionPane.showMessageDialog
                                    (null, "你胜利了"+wipeCount+"个格子", "你胜利了", JOptionPane.ERROR_MESSAGE);

                        }
                        try {
                            eventButton.setIcon(new ImageIcon(new File(SRC_URL+"11"+arr[x][y]+".jpg").toURI().toURL()));
                        } catch (MalformedURLException e1) {
                            e1.printStackTrace();
                        }
                        book[x][y] = 1;
                        String str = arr[x][y]+"";
                        //eventButton.setContentAreaFilled(false);
                        //eventButton.setEnabled(false);
                        //eventButton.setText(str);
                    }else{
                        //表示踩到雷了

                        try {
                            mus = new File(SRC_URL+"4127.wav").toURI().toURL();
                        } catch (MalformedURLException e1) {
                            e1.printStackTrace();
                        }
                        Applet.newAudioClip(mus).play();
                        JOptionPane.showMessageDialog
                                (null, "你踩到了雷,成功排了"+wipeCount+"个格子", "你失败了", JOptionPane.ERROR_MESSAGE);
                        System.exit(0);
                    }
                }else if(e.getButton() == 3){
                    // 表示鼠标右击 标记雷
                    IndexJButton eventButton = (IndexJButton) e.getSource();
                    int x = eventButton.x;
                    int y = eventButton.y;
                    if(book[x][y] != 1 && book[x][y] ==0){
                        // 表示这个格子还没有被打开
                        try {
                            eventButton.setIcon(new ImageIcon(new File(SRC_URL+"biaoji.jpg").toURI().toURL()));
                        } catch (MalformedURLException e1) {
                            e1.printStackTrace();
                        }
                        book[x][y] = 3;//表示这个地方被标记成雷区
                    }else if(book[x][y] == 3){
                        try {
                            eventButton.setIcon(new ImageIcon(new File(SRC_URL+"wen.jpg").toURI().toURL()));
                        } catch (MalformedURLException e1) {
                            e1.printStackTrace();
                        }
                        book[x][y] = 4;
                    }else if(book[x][y] == 4){
                        try {
                            eventButton.setIcon(new ImageIcon(new File(SRC_URL+"repare.jpg").toURI().toURL()));
                        } catch (MalformedURLException e1) {
                            e1.printStackTrace();
                        }
                        book[x][y] = 0;
                    }
                }

            }
        };
		/*
		ActionListener al = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				IndexJButton eventButton = (IndexJButton) e.getSource();
				int x = eventButton.x;
				int y = eventButton.y;
				if(arr[x][y] == 0){
					showRound(x, y);
					for(int m=0;m<book.length;m++){
						for(int n=0;n<book[m].length;n++){
							if(book[m][n] == 1){
								String str;
								if(arr[m][n] == 0){
									 str = "";
								}else{
									 str = arr[m][n]+"";
								}
								button = null;
								button = buttons[m][n];
								button.setContentAreaFilled(false);
								button.setEnabled(false);
								button.setText(str);
								//System.out.println(arr[x][y]);
							}
						}
					}
				}else if(arr[x][y] > 0){
					book[x][y] = 1;
					String str = arr[x][y]+"";
					eventButton.setContentAreaFilled(false);
					eventButton.setEnabled(false);
					eventButton.setText(str);
				}else{
					//表示踩到雷了
					JOptionPane.showMessageDialog
					(null, "你踩到了雷,成功排了"+wipeCount+"个格子", "你失败了", JOptionPane.ERROR_MESSAGE);
					System.exit(0);
				}
			}

		};
		*/
        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr.length;j++){
                buttons[i][j] = new IndexJButton(i,j);
                buttons[i][j].setIcon(new ImageIcon(new File(SRC_URL+"repare.jpg").toURI().toURL()));
                //buttons[i][j].addActionListener(al); //每个按钮的事件处理者都是al
                buttons[i][j].addMouseListener(ml);
                jp.add(buttons[i][j]);
            }
        }
        jf.add(jp);
        jf.pack(); //组件放入完成后，用pack()可以自动调节大小
        jf.setSize(650,650); //设置窗口大小
        jf.setVisible(true); //设置窗口可见，默认是不可见的
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置关闭时退出
        jf.setLocation(300,0);
        jf.setResizable(false);//设置窗口大小不可变
    }
    /**开场音乐*/
    public void sound() throws MalformedURLException {
        //mus = this.getClass().getResource("./resources/8730.wav");
        mus = new File(SRC_URL+"8730.wav").toURI().toURL();
        Applet.newAudioClip(mus).play();
    }
    public static void main(String[] args) throws Exception {

        paintMoRen();
        SweepBySwing sb = new SweepBySwing();
        //System.out.println(sb.getClass().getResource("/").getPath());
        sb.sound();
        sb.produceView();

    }

}
