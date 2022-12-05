public class Snake {
    private Point[] points;
    private final AudColor color = AudColor.GREEN;
    private Direction nextDirection = Direction.RIGHT;
    private Direction lastDirection = Direction.RIGHT;

    /**
     * direction setter, ignores input direction when player movements would directly cause collision
     **/
    public void setNextDirection(Direction direction) {
        if (direction == Direction.UP && lastDirection != Direction.DOWN) {
            nextDirection = direction;
        } else if (direction == Direction.DOWN && lastDirection != Direction.UP) {
            nextDirection = direction;
        } else if (direction == Direction.LEFT && lastDirection != Direction.RIGHT) {
            nextDirection = direction;
        } else if (direction == Direction.RIGHT && lastDirection != Direction.LEFT) {
            nextDirection = direction;
        }

    }

    /**
     * gets the current direction of the snake (on UML diagram return type was Enum, but does not works)
     **/

    public Direction getNextDirection() {
        return nextDirection;
    }


    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    /**
     * copying array of points without last element
     * adding new point to the front of the array depending on the direction
     * ps: coordinates System starts at top left corner
     **/
    public void step() {
        System.arraycopy(points, 0, points, 1, points.length - 1);
        switch (nextDirection) {

            case RIGHT:
                points[0] = new Point(points[1].getX() + 1, points[1].getY());
                lastDirection = getNextDirection();
                break;
            case DOWN:
                points[0] = new Point(points[1].getX(), points[1].getY() + 1);
                lastDirection = getNextDirection();
                break;
            case LEFT:
                points[0] = new Point(points[1].getX() - 1, points[1].getY());
                lastDirection = getNextDirection();
                break;
            case UP:
                points[0] = new Point(points[1].getX(), points[1].getY() - 1);
                lastDirection = getNextDirection();
                break;
        }

    }

    /**
     * snake constructor, creates a snake given the x and y coordinates of the head
     **/

    public Snake(int x, int y, int length) {
        if (length < 0) {
            throw new IllegalArgumentException("Snakes must be longer then zero!");
        } else {
            points = new Point[length];
            points[0] = new Point(x, y);
        }
    }

    /**
     * points array is null when snake is growing or moving
     **/
    public void paint(AudGraphics g) {
        g.setColor(color);
        for (Point point : points)
            if (point != null) {
                g.fillRect(point.getX() * SnakeGame.SQUARE_SIZE, point.getY() * SnakeGame.SQUARE_SIZE,
                        SnakeGame.SQUARE_SIZE, SnakeGame.SQUARE_SIZE);
            }
    }

    /**
     * checks if snake is colliding with a game item or itself by checking if the head
     * is in the same position as any other point
     **/
    public boolean collidesWith(GameItem item) {
        return collidesWith(item.getPosition().getX(), item.getPosition().getY());
    }

    public boolean collidesWith(int x, int y) {
        return points[0].getX() == x && points[0].getY() == y;
    }


    public boolean collidesWithSelf() {
        return snakeContains(points[0].getX(), points[0].getY());
    }


    /**
     * auxiliary method to
     * check if snake contains input coordinates in order to prevent spawning inside snake body
     **/
    public boolean snakeContains(int x, int y) {
        int i = 1;
        while (i < points.length) {
            if (points[i] == null) {
                return false;
            }
            if (points[i].getX() == x && points[i].getY() == y) {
                return true;
            }
            i++;
        }
        return false;
    }

    /**
     * copies old array from head to tail to a new array that has the new length: old length + amount
     * new array is then assigned to 'old' array
     **/
    public void grow(int amount) {
        if (amount < SnakeGame.GROW_AMOUNT || amount < 1) {
            throw new IllegalArgumentException("Grow Amount must be positive and greater than one");
        }
        Point[] growthPoint = new Point[points.length + amount];
        System.arraycopy(points, 0, growthPoint, 0, points.length - 1);
        points = growthPoint;
    }


    /**
     * overloaded method, creates a snake with a standard length of 5 SQUARE_SIZE
     **/
    public Snake(int x, int y) {
        this(x, y, 5);
    }

}
