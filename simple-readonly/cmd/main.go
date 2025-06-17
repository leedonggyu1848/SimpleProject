package main

import (
	"context"
	"log"
	"os"
	"time"

	"simple-readonly/internal/handler"
	"simple-readonly/internal/repository"
	"simple-readonly/internal/router"
	"simple-readonly/internal/service"

	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
)

func main() {
    ctx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
    defer cancel()

    mongoURI := getEnv("MONGO_URI", "mongodb://root:example@localhost:27017/?authSource=admin")
    mongoDBName := getEnv("MONGO_DB", "testdb")
    trashCollection := getEnv("MONGO_COLLECTION", "trashRead")
    log.Printf("Connecting to MongoDB at %s, database: %s, collection: %s", mongoURI, mongoDBName, trashCollection)
    client, err := mongo.Connect(ctx, options.Client().ApplyURI(mongoURI))
    if err != nil {
        log.Fatal(err)
    }
    err = client.Ping(ctx, nil)
    if err != nil {
        log.Fatal("Failed to connect to MongoDB:", err)
    }
    collection := client.Database(mongoDBName).Collection(trashCollection)

    // Trash domain setup
    repo := repository.NewMongoTrashRepository(collection)
    svc := service.NewTrashService(repo)
    h := handler.NewTrashHandler(svc)

    router.SetupRouter(h).Run(":8000")
}

func getEnv(key, defaultVal string) string {
    if val, exists := os.LookupEnv(key); exists {
        return val
    }
    return defaultVal
}