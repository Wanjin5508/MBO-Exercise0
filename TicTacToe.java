package MBO;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class TicTacToe extends JApplet
{
    //初始指定X先走
    private char whoseturn='X';
    //创建井字棋棋格【3*3】
    private Cell[][] cell=new Cell[3][3];
    //创建标签用于显示当前对局状态
    private JLabel jlblStatus=new JLabel("X  turn  to play");
    // 创建重新开始按钮
    private JButton restartButton = new JButton("Restart");

    public void init()//初始化各组件
    {
        //创建显示面板并设置布局、边界
        JPanel p=new JPanel();
        p.setLayout(new GridLayout(3,3,0,8));
        p.setBorder(new LineBorder(Color.black,1));

        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                p.add(cell[i][j]=new Cell());

        jlblStatus.setBorder(new LineBorder(Color.red,1));
        // 添加重新开始按钮并添加监听器
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });

        // 创建一个新的面板，将状态标签和重新开始按钮放在一起
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.add(jlblStatus, BorderLayout.CENTER);
        statusPanel.add(restartButton, BorderLayout.EAST);

        // 将状态标签和显示面板加入内容面板并设置其方位
        this.getContentPane().add(p, BorderLayout.CENTER);
        this.getContentPane().add(statusPanel, BorderLayout.SOUTH);
    }

    // 重置游戏状态
    private void restartGame() {
        whoseturn = 'X';
        jlblStatus.setText("X turn to play");
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                cell[i][j].setToken(' ');
    }


    public static void main(String[] args)
    {
        JFrame frame=new JFrame("Tic Tac Toe");
        TicTacToe applet=new TicTacToe();
        frame.getContentPane().add(applet,BorderLayout.CENTER);

        applet.init();
        applet.start();
        //设置初始棋盘大小、可见性、默认关闭操作
        frame.setSize(300,300);
        frame.setVisible(true);//默认不可见
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//关闭即退出
    }
    //判断棋盘是否下满
    public boolean isFull()
    {
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                if(cell[i][j].getToken()==' ')
                    return false;
        return true;
    }
    //判断胜利的几种的状态
    public boolean isWon(char token)
    {
        //某行连续三个
        for(int i=0;i<3;i++)
            if((cell[i][0].getToken()==token)
                    &&(cell[i][1].getToken()==token)
                    &&(cell[i][2].getToken()==token))
                return true;
        //某列连续三个
        for(int j=0;j<3;j++)
            if((cell[0][j].getToken()==token)
                    &&(cell[1][j].getToken()==token)
                    &&(cell[2][j].getToken()==token))
                return true;
        //主对角线三个
        if((cell[0][0].getToken()==token)
                &&(cell[1][1].getToken()==token)
                &&(cell[2][2].getToken()==token))
            return true;
        //副对角线三个
        if((cell[0][2].getToken()==token)
                &&(cell[1][1].getToken()==token)
                &&(cell[2][0].getToken()==token))
            return true;

        return false;

    }

    class Cell extends JPanel implements MouseListener
    {
        private char token=' ';
        //为棋格添加监听器
        public Cell()
        {
            setBorder(new LineBorder(Color.black));
            addMouseListener(this);
        }
        public char getToken()
        {
            return token;
        }
        public void setToken(char c)
        {
            token=c;
            repaint();//重画棋盘，刷新显示
        }
        //对于空棋格，点哪就在哪画对应的X或O
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            if(token=='X')
            {
                g.drawLine(10,10,
                        getSize().width-10,
                        getSize().height-10);
                g.drawLine(getSize().width-10,
                        10,10,
                        getSize().height-10);
            }
            else if(token=='O')
                g.drawOval(10,10,
                        getSize().width-20,
                        getSize().height-20);
        }

        public void mousePressed(MouseEvent e){}
        public void mouseReleased(MouseEvent e){}
        public void mouseEntered(MouseEvent e){}
        public void mouseExited(MouseEvent e){}
        //只需重写该方法即可
        public void mouseClicked(MouseEvent e)
        {
            if(token==' ')//只能在空棋格下棋
            {
                if(whoseturn=='X')//若当前为X走
                {
                    setToken('X');//设置说要画的形状为X
                    whoseturn='O';//并更新下一步为O走
                    //在状态标签显示
                    jlblStatus.setText("O turn to play");
                    if(isWon('X'))
                        jlblStatus.setText("X won. Over");
                        //棋盘下满了仍未分出胜负，平局
                    else if(isFull())
                        jlblStatus.setText("draw.game over");
                }
                else if(whoseturn=='O')
                {
                    setToken('O');
                    whoseturn='X';
                    jlblStatus.setText("X turn to play");
                    if(isWon('O'))
                        jlblStatus.setText("O won. Over");
                    else if(isFull())
                        jlblStatus.setText("draw.game over");
                }
            }

        }
    }
}