package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Map extends JPanel {
    private static final Random RANDOM = new Random();
    private static final int DOT_PADDING = 5;

    private boolean isGameOver;
    private boolean isInitialized;

    private int gameOverType;
    private final int STATE_DRAW = 0;
    private final int STATE_WIN_HUMAN = 1;
    private final int STATE_WIN_AI = 2;

    private static final String MSG_WIN_HUMAN = "Igrok pobedil";
    private static final String MSG_WIN_AI = "KOMP pobedil";
    private static final String MSG_DRAW = "NICHIA";

    private final int Human_dot = 1;
    private final int AI_dot = 2;
    private final int Empty_dot = 0;
    private int fieldSizeY = 3;
    private int fieldSizeX = 3;
    private char[][] field;
    private int winLength = 3;

    private void initMap() {
        field = new char[fieldSizeY][fieldSizeX];
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                field[i][j] = Empty_dot;
            }
        }
    }

    private boolean isValidCell(int x, int y) {
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }

    private boolean isEmptyCell(int x, int y) {
        return field[y][x] == Empty_dot;
    }

    private void aiTurn() {
        // Попытка выиграть самому
        if (tryWin(AI_dot)) {
            return;
        }

        // Попытка блокировать ход игрока
        if (tryBlock(Human_dot)) {
            return;
        }

        // Если не удалось выиграть или заблокировать, делаем случайный ход
        int x, y;
        do {
            x = RANDOM.nextInt(fieldSizeX);
            y = RANDOM.nextInt(fieldSizeY);
        } while (!isEmptyCell(x, y));
        field[y][x] = AI_dot;
    }

    private boolean tryWin(int dot) {
        // Анализируем, есть ли возможность выиграть
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                if (isEmptyCell(j, i)) {
                    field[i][j] = (char) dot;
                    if (CheckWin((char) dot)) {
                        return true;
                    }
                    field[i][j] = Empty_dot;
                }
            }
        }
        return false;
    }

    private boolean tryBlock(int dot) {
        // Анализируем, есть ли необходимость заблокировать игрока
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                if (isEmptyCell(j, i)) {
                    field[i][j] = (char) dot;
                    if (CheckWin((char) dot)) {
                        // Блокируем ход, если это приведет к победе игрока
                        field[i][j] = AI_dot;
                        return true;
                    }
                    field[i][j] = Empty_dot;
                }
            }
        }
        return false;
    }

    private int panelWidth;
    private int panelHeight;
    private int cellHeight;
    private int cellWidth;

    Map() {
        setBackground(Color.BLACK);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                update(e);
            }
        });
        isInitialized = false;
    }

    void startNewGame(int mode, int fSzX, int fSzY, int wLen) {
        System.out.printf("Mode: %d;\nSize: x=%d,y=%d;\nWin Length: %d", mode, fSzX, fSzY, wLen);
        fieldSizeX = fSzX;
        fieldSizeY = fSzY;
        winLength = wLen;
        initMap();
        isGameOver = false;
        isInitialized = true;
        repaint();
    }

    private void update(MouseEvent e) {
        if (isGameOver || !isInitialized) return;
        int cellX = e.getX() / cellWidth;
        int cellY = e.getY() / cellHeight;
        if (!isValidCell(cellX, cellY) || !isEmptyCell(cellX, cellY)) return;
        field[cellY][cellX] = Human_dot;
        if (checkEndGame(Human_dot, STATE_WIN_HUMAN)) return;
        aiTurn();
        repaint();
        if (checkEndGame(AI_dot, STATE_WIN_AI)) return;
    }

    private boolean checkEndGame(int dot, int gameOverType) {
        if (CheckWin((char) dot)) {
            this.gameOverType = gameOverType;
            isGameOver = true;
            repaint();
            return true;
        }
        if (isMapFill()) {
            this.gameOverType = STATE_DRAW;
            isGameOver = true;
            repaint();
            return true;
        }
        return false;
    }

    private boolean CheckWin(char c) {
        // Проверка по горизонтали и вертикали
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j <= fieldSizeX - winLength; j++) {
                boolean horizontalWin = true;
                boolean verticalWin = true;
                for (int k = 0; k < winLength; k++) {
                    if (field[i][j + k] != c) {
                        horizontalWin = false;
                    }
                    if (field[j + k][i] != c) {
                        verticalWin = false;
                    }
                }
                if (horizontalWin || verticalWin) {
                    return true;
                }
            }
        }

        // Проверка по диагонали
        for (int i = 0; i <= fieldSizeY - winLength; i++) {
            for (int j = 0; j <= fieldSizeX - winLength; j++) {
                boolean diagonalWin1 = true;
                boolean diagonalWin2 = true;
                for (int k = 0; k < winLength; k++) {
                    if (field[i + k][j + k] != c) {
                        diagonalWin1 = false;
                    }
                    if (field[i + k][j + winLength - 1 - k] != c) {
                        diagonalWin2 = false;
                    }
                }
                if (diagonalWin1 || diagonalWin2) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        render(g);
    }

    private void render(Graphics g) {
        if (!isInitialized) return;
        panelWidth = getWidth();
        panelHeight = getHeight();
        cellHeight = panelHeight / fieldSizeY;
        cellWidth = panelWidth / fieldSizeX;

        g.setColor(Color.WHITE);
        for (int h = 0; h < fieldSizeY; h++) {
            int y = h * cellHeight;
            g.drawLine(0, y, panelWidth, y);
        }
        for (int w = 0; w < fieldSizeX; w++) {
            int x = w * cellWidth;
            g.drawLine(x, 0, x, panelHeight);
        }
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if (field[y][x] == Empty_dot) continue;

                if (field[y][x] == Human_dot) {
                    g.setColor(Color.BLUE);
                    g.fillOval(x * cellWidth + DOT_PADDING,
                            y * cellHeight + DOT_PADDING,
                            cellWidth - DOT_PADDING * 2,
                            cellHeight - DOT_PADDING * 2);
                } else if (field[y][x] == AI_dot) {
                    g.setColor(new Color(0xff0000));
                    g.fillOval(x * cellWidth + DOT_PADDING,
                            y * cellHeight + DOT_PADDING,
                            cellWidth - DOT_PADDING * 2,
                            cellHeight - DOT_PADDING * 2);

                } else {
                    throw new RuntimeException("un ex value " + field[y][x]);
                }
            }
        }
        if (isGameOver) showMassageGameOVer(g);
    }

    private void showMassageGameOVer(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 200, getWidth(), 70);
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Times new roman", Font.BOLD, 48));
        switch (gameOverType) {
            case STATE_DRAW:
                g.drawString(MSG_DRAW, 180, getHeight() / 2);
                break;
            case STATE_WIN_AI:
                g.drawString(MSG_WIN_AI, 20, getHeight() / 2);
                break;
            case STATE_WIN_HUMAN:
                g.drawString(MSG_WIN_HUMAN, 70, getHeight() / 2);
                break;
            default:
                throw new RuntimeException("Un ex" + gameOverType);
        }
    }

    private boolean isMapFill() {
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                if (field[i][j] == Empty_dot) return false;
            }
        }
        return true;
    }
}
