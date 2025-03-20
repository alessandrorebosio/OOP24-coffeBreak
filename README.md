# CoffeBreak

CoffeBreak ha le stesse meccaniche di Donkey Kong. Uno studente cerca di farsi la sua pausa caffè e deve raggiungere la macchinetta che si trova in cima ad una struttura protetta dal tecnico delle macchinette. Quest'ultimo, ben consapevole del letale rischio di bersi 6 caffe al giorno, cerca di impedire la scalata al povero laureando tirandogli bottiglie di fluidi senza caffeina. L'idratazione è importante. Lo studente ha solo due modi per evitarle: saltarle o usando il power up. Durante il tragitto, si potranno trovare delle monetine che verranno raccolte immediatamente. Per raggiungere il suo obiettivo avrà a disposizione 3 appelli (vite) alla fine dei quali sarà costretto a rinunciare agli studi, la stanchezza non perdona!

# Email dei componenti:
 - grazia.bochdanovits@studio.unibo.it
 - lorenzo.boccuzzi@studio.unibo.it
 - alessandro.rebosio@studio.unibo.it
 - filippo.ricciotti@studio.unibo.it

# Diagramma UML
```mermaid
classDiagram

    %% === MODEL ===
    class GameEntity {
        +update()
        +render()
    }

    class Player {
        +move()
        +jump()
        +collectItem()
    }

    class Enemy {
        +move()
        +attack()
    }

    class Barrel {
        +roll()
    }

    class Fire {
        +chasePlayer()
    }

    class Platform {
        +supportPlayer()
    }

    class Collectible {
        +applyEffect()
    }

    class GameState {
        +currentState: String
        +setState()
    }

    class ScoreManager {
        +score: int
        +increaseScore()
    }

    class CollisionManager {
        +checkCollisions()
    }

    class DifficultyManager {
        +increaseDifficulty()
    }

    class SoundManager {
        +playSound()
        +stopSound()
    }

    class GameEngine {
        +updateGame()
        +renderGame()
    }

    class InputManager {
        +processInput()
    }

    class PhysicsManager {
        +applyGravity()
        +resolveCollisions()
    }

    GameEntity <|-- Player
    GameEntity <|-- Enemy
    Enemy <|-- Barrel
    Enemy <|-- Fire
    GameEntity <|-- Platform
    GameEntity <|-- Collectible
    Collectible <|-- Coin
    Collectible <|-- PowerUp

    GameState <|-- MenuState
    GameState <|-- InGameState
    GameState <|-- PauseState
    GameState <|-- GameOverState

    GameEngine *-- GameState
    GameEngine *-- ScoreManager
    GameEngine *-- CollisionManager
    GameEngine *-- DifficultyManager
    GameEngine *-- SoundManager
    GameEngine *-- Player
    GameEngine *-- Enemy
    GameEngine *-- Platform
    GameEngine *-- Collectible
    GameEngine *-- PhysicsManager

    <<Interface>> GameEntity 
    <<Interface>> InputManager 

    %% === VIEW ===
    class GameView {
        +render()
    }

    GameView <|-- MenuView
    GameView <|-- InGameView
    GameView <|-- PauseView
    GameView <|-- GameOverView

    GameView o-- GameEngine

    %% === CONTROLLER ===
    class GameController {
        +handleInput()
    }

    GameController --> GameEngine
    InputManager --> GameController
    GameView --> InputManager

    %% Entry Points
    GameController <..> GameView : "User Input"
    GameView <..> GameEngine : "Render Update"
    GameController <..> GameEngine : "Game Logic"
```
