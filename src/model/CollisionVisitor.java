package model;

public interface CollisionVisitor {
    void visit(Asteroid asteroid);
    void visit(Spaceship spaceship);
    void visit(EnemyUFO ufo);
    void visit(Bullet bullet);
    void visit(Missile missile);
    void visit(Boss boss);
    void visit(ForceField forcefield);
}
