package code;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class ManCtrl {
    private Action actiontaken=Action.NONE;
    public ManCtrl(Grid grid_object, RescueBoat coast_guard,
                   ArrayList<Station> station_list, ArrayList<Ship> ship_list,
                   boolean coast_guard_at_ship, boolean coast_guard_at_station) {

                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }

                JFrame frame = new JFrame("Project 1: Coast Guard");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                frame.setLayout(new BorderLayout());

                GridView gridview_object = new GridView(grid_object, coast_guard,
                        station_list, ship_list, coast_guard_at_ship, coast_guard_at_station);
                frame.add(gridview_object);

                frame.pack();
                frame.setLocationRelativeTo(null);

                frame.setVisible(true);

    }

    public Action get_manual_action()
    {
        return actiontaken;
    }

    public class GridView extends JPanel implements KeyListener {

        private int columnCount = 0;
        private int rowCount = 0;

        private List<Rectangle> cells;
        private ArrayList<pair> station_list_pair = new ArrayList<pair>();
        private ArrayList<pair> ship_list_pair = new ArrayList<pair>();
        private pair coastguard_pair = new pair(0, 0);
        ;
        private boolean coast_guard_at_ship_check = false;
        private boolean coast_guard_at_station_check = false;
        private boolean no_key_pressed = true;

        public GridView(Grid grid_object, RescueBoat coast_guard,
                        ArrayList<Station> station_list, ArrayList<Ship> ship_list,
                        boolean coast_guard_at_ship, boolean coast_guard_at_station) {
            columnCount = grid_object.getM();
            rowCount = grid_object.getN();
            cells = new ArrayList<>(columnCount * rowCount);
            parse_objects_locations(grid_object, coast_guard, station_list, ship_list,
                    coast_guard_at_ship, coast_guard_at_station);
            JFrame frame = new JFrame("Key Listener");
            Container contentPane = frame.getContentPane();

            while (no_key_pressed == true) {
                KeyListener k = new KeyListener() {

                    @Override
                    public void keyTyped(KeyEvent e) {
                    }
                    @Override
                    public void keyPressed(KeyEvent e) {
                        int keyCode = e.getKeyCode();
                        if (keyCode == KeyEvent.VK_UP) {
                            no_key_pressed = false;
                            actiontaken = Action.UP;
                        } else if (keyCode == KeyEvent.VK_DOWN) {
                            no_key_pressed = false;
                            actiontaken = Action.DOWN;
                        } else if (keyCode == KeyEvent.VK_LEFT) {
                            no_key_pressed = false;
                            actiontaken = Action.LEFT;
                        } else if (keyCode == KeyEvent.VK_RIGHT) {
                            no_key_pressed = false;
                            actiontaken = Action.RIGHT;
                        }
                        repaint();

                    }

                    @Override
                    public void keyReleased(KeyEvent e) {
                    }


                };
                JTextField textField = new JTextField();
                textField.addKeyListener(k);
                contentPane.add(textField, BorderLayout.NORTH);
                frame.pack();
                frame.setVisible(true);
            }
        }

        private void parse_objects_locations(Grid grid_object, RescueBoat coast_guard,
                                             ArrayList<Station> station_list, ArrayList<Ship> ship_list,
                                             boolean coast_guard_at_ship, boolean coast_guard_at_station) {
            // coastguard locations
            coastguard_pair.setFirstValue(coast_guard.getLocation().getFirstValue());
            coastguard_pair.setSecondValue(coast_guard.getLocation().getSecondValue());

            // coastguard_ship locations
            if (coast_guard_at_ship) {
                coast_guard_at_ship_check = true;
            }
            // coastguard_ship locations
            else if (coast_guard_at_station) {
                coast_guard_at_station_check = true;
            }
            // is there a size or should we use the getter function TODO
            // check here if all ships have vanished
            for (int j = 0; j < ship_list.size(); j++) {
                pair temp_pair = new pair(ship_list.get(j).getLocation().getFirstValue(),
                        ship_list.get(j).getLocation().getSecondValue());
                ship_list_pair.add(temp_pair);
            }

            // is there a size or should we use the getter function
            for (int j = 0; j < station_list.size(); j++) {
                pair temp_pair = new pair(station_list.get(j).getLocation().getFirstValue(),
                        station_list.get(j).getLocation().getSecondValue());
                station_list_pair.add(temp_pair);
            }

        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(600, 600);
        }

        /**
         * If this function is uncommented it is called without me explcicity calling it
         * and it sets the coastguard_pair to null making it invisible
         * as a hack it will be uncommented until it appears to be useful
         *
         * @Override public void invalidate() {
         * cells.clear();
         * coastguard_pair = null;
         * super.invalidate();
         * }
         **/

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();

            int width = getWidth();
            int height = getHeight();

            int cellWidth = width / columnCount;
            int cellHeight = height / rowCount;

            int xOffset = (width - (columnCount * cellWidth)) / 2;
            int yOffset = (height - (rowCount * cellHeight)) / 2;

            if (cells.isEmpty()) {
                for (int row = 0; row < rowCount; row++) {
                    for (int col = 0; col < columnCount; col++) {
                        Rectangle cell = new Rectangle(
                                xOffset + (col * cellWidth),
                                yOffset + (row * cellHeight),
                                cellWidth,
                                cellHeight);
                        cells.add(cell);
                        g2d.setColor(Color.WHITE);
                        g2d.fill(cell);
                    }
                }
            }

            if (coast_guard_at_ship_check && coastguard_pair != null) {
                int index = coastguard_pair.getFirstValue() + (coastguard_pair.getSecondValue() * columnCount);
                Rectangle cell = cells.get(index);
                g2d.setColor(Color.YELLOW);
                g2d.fill(cell);
            } else if (coast_guard_at_station_check && coastguard_pair != null) {
                int index = coastguard_pair.getFirstValue() + (coastguard_pair.getSecondValue() * columnCount);
                Rectangle cell = cells.get(index);
                g2d.setColor(Color.GREEN);
                g2d.fill(cell);
            }
            if (coastguard_pair != null) {

                int index = coastguard_pair.getFirstValue() + (coastguard_pair.getSecondValue() * columnCount);
                Rectangle cell = cells.get(index);
                g2d.setColor(Color.BLUE);
                g2d.fill(cell);
            }


            if (!ship_list_pair.isEmpty()) {
                for (int j = 0; j < ship_list_pair.size(); j++) {
                    int index = ship_list_pair.get(j).getFirstValue() + (ship_list_pair.get(j).getSecondValue() * columnCount);
                    Rectangle cell = cells.get(index);
                    g2d.setColor(Color.RED);
                    g2d.fill(cell);
                }
            }

            if (!station_list_pair.isEmpty()) {
                for (int j = 0; j < station_list_pair.size(); j++) {
                    int index = station_list_pair.get(j).getFirstValue() + (station_list_pair.get(j).getSecondValue() * columnCount);
                    Rectangle cell = cells.get(index);
                    g2d.setColor(Color.BLACK);
                    g2d.fill(cell);
                }
            }
            g2d.setColor(Color.GRAY);
            for (Rectangle cell : cells) {
                g2d.draw(cell);
            }

            g2d.dispose();

        }


        @Override
        public void keyTyped(KeyEvent e) {
            ;
        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    }
}
