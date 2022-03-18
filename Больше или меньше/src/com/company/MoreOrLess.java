package com.company;

import javax.swing.*;
import javax.swing.UIManager.*;

public class MoreOrLess extends JFrame {
    //java swing fields
    private JTextField leftBoarderText;
    private JTextField rightBoarderText;
    private JButton applyButton;
    private JButton lessButton;
    private JButton moreButton;
    private JLabel leftBoarderLabel;
    private JLabel rightBoarderLabel;
    private JPanel mainPanel;
    private JLabel predictionLabel;
    private JButton resetButton;
    private JButton trueNumButton;
    private JRadioButton metalDefaultRadioButton;
    private JRadioButton nimbusRadioButton;
    private JRadioButton CDEButton;

    //user fields
    private int leftBoarder, rightBoarder;
    private int predictionIndex;
    private int[] range;
    private int tries;

    //constructor for main frame
    MoreOrLess()
    {
        //title for main frame
        super("Больше или меньше");
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        //Primary settings
        predictionLabel.setText("Здесь будет ваше число");
        moreButton.setEnabled(false);
        lessButton.setEnabled(false);
        trueNumButton.setEnabled(false);

        //Button groups for radio buttons
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(metalDefaultRadioButton);
        buttonGroup.add(nimbusRadioButton);
        buttonGroup.add(CDEButton);

        metalDefaultRadioButton.setSelected(true);

        //Radio buttons switch styles
        metalDefaultRadioButton.addActionListener(e -> styleSwitcher("Metal"));
        CDEButton.addActionListener(e -> styleSwitcher("CDE/Motif"));
        nimbusRadioButton.addActionListener(e -> styleSwitcher("Nimbus"));

        //Functional buttons
        resetButton.addActionListener(e -> resetData());

        applyButton.addActionListener(e -> {
            //взять значения из левой и правой границы и их проанализировать
            String tempLeft = leftBoarderText.getText();
            String tempRight = rightBoarderText.getText();
            if (isInt(tempLeft) && isInt(tempRight))
            {
                rightBoarder = Integer.parseInt(tempRight);
                leftBoarder = Integer.parseInt(tempLeft);
                if (rightBoarder > leftBoarder)
                {
                    JOptionPane.showMessageDialog(mainPanel, "Успешно! Загадывайте число из введенного вами диапазона.");
                    range = new int[rightBoarder - leftBoarder + 1];
                    for (int i = 0; i<rightBoarder - leftBoarder + 1; i++)
                    {
                        range[i] = i+leftBoarder;
                    }
                    moreButton.setEnabled(true);
                    lessButton.setEnabled(true);
                    trueNumButton.setEnabled(true);
                    predictor();
                }
                else
                {
                    JOptionPane.showMessageDialog(mainPanel, "Введены некорректные значения!");
                    clearTextFields();
                }
            }
            else
            {
                JOptionPane.showMessageDialog(mainPanel, "Введены некорректные значения (или ничего не введено)!");
                clearTextFields();
            }

        });

        moreButton.addActionListener(e -> {
            leftBoarder = predictionIndex + 1;
            predictor();
        });

        lessButton.addActionListener(e -> {
            rightBoarder = predictionIndex - 1;
            predictor();
        });

        trueNumButton.addActionListener(e -> endGameMessage());

        setVisible(true);
    }

    //a method that switching styles (style in string format in arg)
    private void styleSwitcher(String style)
    {
        for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if (style.equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                } catch (InstantiationException ex) {
                    ex.printStackTrace();
                } catch (IllegalAccessException ex) {
                    ex.printStackTrace();
                } catch (UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }
                break;
            }
        }
        mainPanel.updateUI();
    }

    //a functional method that predict number
    private void predictor()
    {
        tries++;
        predictionIndex = (leftBoarder + rightBoarder) / 2;
        predictionLabel.setText("Ваше число: " + range[predictionIndex] + "?");
        if (leftBoarder == rightBoarder)
        {
            endGameMessage();
        }
    }

    //a method that determines whether the data in a string belongs to a numeric type (string that can contains numbers in arg)
    private boolean isInt(String s) throws NumberFormatException {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //a method that clearing input fields
    private void clearTextFields()
    {
        rightBoarderText.setText("");
        leftBoarderText.setText("");
    }

    //a method that calling dialog frame and determines what to do
    private void endGameMessage()
    {
        if (JOptionPane.showConfirmDialog(mainPanel, "Ваше число: " + range[predictionIndex] + ". Сделано попыток: " + tries + "!\n"
                        + "Хотите сыграть ещё раз?", "Успешно!",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            // yes option
            resetData();

        } else {
            // no option
            System.exit(0);
        }
    }

    //a method that clearing and reset all data
    private void resetData()
    {
        JOptionPane.showMessageDialog(mainPanel,"Данные сброшены!");
        clearTextFields();
        predictionLabel.setText("Здесь будет ваше число");
        moreButton.setEnabled(false);
        lessButton.setEnabled(false);
        trueNumButton.setEnabled(false);
    }
}
