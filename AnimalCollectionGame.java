import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class AnimalCollectionGame extends JPanel implements ActionListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int TIMER_DELAY = 3000; // in milliseconds
    private static final int ICON_SIZE = 50; // Desired icon size (both width and height)

    private int score = 0;
    private Timer timer;
    private Random random;
    private List<Animal> animals;

    public AnimalCollectionGame() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.white);

        // Create a timer to spawn new animals
        timer = new Timer(TIMER_DELAY, this);
        timer.start();

        // Initialize the random number generator
        random = new Random();

        // Initialize the list of animals
        animals = new ArrayList<>();

        // Set up mouse click listener
        addMouseListener(new MouseClickListener());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the animal icons
        for (Animal animal : animals) {
            animal.draw(g);
        }

        // Display the current score
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("Score: " + score, 20, 30);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Add a new random animal to the list
        int x = random.nextInt(WIDTH - ICON_SIZE);
        int y = random.nextInt(HEIGHT - ICON_SIZE);
        int animalIndex = random.nextInt(5); // Randomly choose an animal
        animals.add(new Animal(x, y, animalIndex));

        repaint(); // Redraw the board to display new animals
    }

    private class MouseClickListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            // Check if the mouse click is on an animal icon
            List<Animal> animalsToRemove = new ArrayList<>();
            for (Animal animal : animals) {
                if (animal.contains(e.getX(), e.getY())) {
                    // Clicked on an animal, increase score and mark it for removal
                    score++;
                    animalsToRemove.add(animal);
                }
            }

            // Remove the collected animals from the list
            animals.removeAll(animalsToRemove);
            repaint();
        }
    }

    private class Animal {
        private int x;
        private int y;
        private ImageIcon icon;

        public Animal(int x, int y, int animalIndex) {
            this.x = x;
            this.y = y;

            // Load animal icon (replace these paths with your own images)
            icon = scaleIcon(new ImageIcon("C:\\Users\\sinan\\OneDrive\\Desktop\\AnimalCollectionGame\\icon" + (animalIndex + 1) + ".png"));
        }

        public void draw(Graphics g) {
            icon.paintIcon(AnimalCollectionGame.this, g, x, y);
        }

        public boolean contains(int mouseX, int mouseY) {
            return mouseX >= x && mouseX <= x + ICON_SIZE && mouseY >= y && mouseY <= y + ICON_SIZE;
        }
    }

    private ImageIcon scaleIcon(ImageIcon originalIcon) {
        Image image = originalIcon.getImage();
        Image scaledImage = image.getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Animal Collection Game");
            AnimalCollectionGame game = new AnimalCollectionGame();
            frame.add(game);
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}



