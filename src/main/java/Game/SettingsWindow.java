package Game;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsWindow extends JFrame {
    private static final int WINDOW_HEIGHT = 236;
    private static final int WINDOW_WIDTH = 350;

    private JButton btnStart = new JButton("Start NEW GAME");
    private JRadioButton humanVsComputerRadioButton = new JRadioButton("Человек против компьютера");
    private JRadioButton humanVsHumanRadioButton = new JRadioButton("Человек против человека");
    private JLabel fieldSizeLabel = new JLabel("Выберите размеры поля");
    private JLabel currentFieldSizeLabel = new JLabel("Установленный размер поля:");
    private JSlider fieldSizeSlider = new JSlider(3, 10);
    private JLabel winLengthLabel = new JLabel("Выберите длину для победы");
    private JLabel currentWinLengthLabel = new JLabel("Установленная длина:");
    private JSlider winLengthSlider = new JSlider(3, 10);

    SettingsWindow(GameWindow gameWindow) {
        setLocationRelativeTo(gameWindow);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLayout(new GridLayout(0, 1));

        // Добавление радио-кнопок в группу
        ButtonGroup modeGroup = new ButtonGroup();
        modeGroup.add(humanVsComputerRadioButton);
        modeGroup.add(humanVsHumanRadioButton);

        // Добавление слушателя для отображения текущих значений слайдеров в лейблах
        fieldSizeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                currentFieldSizeLabel.setText("Установленный размер поля: " + fieldSizeSlider.getValue());
                // Автоматическое регулирование максимального значения слайдера выигрышной длины
                winLengthSlider.setMaximum(fieldSizeSlider.getValue());
            }
        });

        winLengthSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                currentWinLengthLabel.setText("Установленная длина: " + winLengthSlider.getValue());
            }
        });

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int mode = 0; // Предположим, что по умолчанию выбран режим "Человек против компьютера"

                // Определение выбранного режима
                if (humanVsHumanRadioButton.isSelected()) {
                    mode = 1; // Режим "Человек против человека"
                }

                int fieldSize = fieldSizeSlider.getValue();
                int winLength = winLengthSlider.getValue();

                gameWindow.startNewGame(mode, fieldSize, fieldSize, winLength);
                setVisible(false);
            }
        });

        add(humanVsComputerRadioButton);
        add(humanVsHumanRadioButton);
        add(fieldSizeLabel);
        add(currentFieldSizeLabel);
        add(fieldSizeSlider);
        add(winLengthLabel);
        add(currentWinLengthLabel);
        add(winLengthSlider);
        add(btnStart);
    }
}//
