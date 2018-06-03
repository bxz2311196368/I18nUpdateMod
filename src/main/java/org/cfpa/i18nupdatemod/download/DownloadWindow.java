package org.cfpa.i18nupdatemod.download;

import org.cfpa.i18nupdatemod.config.MainConfig;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class DownloadWindow {
    private DownloadManager manager;
    private JFrame frame;
    private JProgressBar bar;

    /**
     * 弹出一个窗口，包含一个进度条显示下载的进度
     *
     * @param manager 对应的DownloadManager对象
     */
    public DownloadWindow(DownloadManager manager) {
        this.manager = manager;
        init();
    }

    private void init() {
        // 初始化窗口
        frame = new JFrame();
        Integer width = 450;
        Integer height = 130;
        frame.setBounds((Toolkit.getDefaultToolkit().getScreenSize().width - width) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - height) / 25 * 10, width, height);
        frame.setTitle(MainConfig.download.dlWindowsName);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        frame.setContentPane(contentPane);
        contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        // 绘制进度条
        bar = new JProgressBar();
        bar.setPreferredSize(new Dimension(width / 5 * 4, height / 4));
        bar.setStringPainted(true);
        contentPane.add(bar);
        // 在下载未完成时禁止玩家关闭窗口
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // 取消按钮
        JButton bt = new JButton("取消下载") {
            @Override
            protected void fireActionPerformed(ActionEvent event) {
                super.fireActionPerformed(event);
                manager.cancel();
                frame.setVisible(false);
            }
        };
        bt.setLayout(new GridLayout(3, 2, 5, 5));
        contentPane.add(bt);

        // 进度条更新线程
        new Thread(() -> {
            while (!manager.isDone()) {
                bar.setValue((int) (manager.getCompletePercentage() * 100));
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignore) {

                }
            }
            onDownloadFinish();
            if (manager.getStatus() == DownloadStatus.FAIL) {
                bar.setString("下载失败！");
                // 如果下载失败允许玩家关闭窗口
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            } else {
                // 如果下载完成自动关闭窗口
                frame.setVisible(false);
            }
        }, "I18n-Window-Thread").start();
    }

    public void showWindow() {
        frame.setVisible(true);
    }

    public void hide() {
        frame.setVisible(false);
    }

    private static void onDownloadFinish() {
        //TODO，其实我也不知道要做啥
    }
}
