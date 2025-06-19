package config

import (
	"context"
	"log"
	"sync"
	"time"

	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
)

var (
	mongoOnce		sync.Once
	mongoClient		*mongo.Client
)

func GetMongoClient(url string) *mongo.Client {
	mongoOnce.Do(func() {
		ctx, cancel := context.WithTimeout(context.Background(), 5*time.Second)
		defer cancel()

		client, err := mongo.Connect(ctx, options.Client().ApplyURI(url))
		if err != nil {
			   log.Fatalf("Failed to connect to MongoDB: %s", err.Error())
		}
		if err = client.Ping(ctx, nil); err != nil {
			log.Fatalf("Failed to ping MongoDB: %s", err.Error())
		}
		log.Printf("Connected to MongoDB at %s", url)
		mongoClient = client
	})
	return mongoClient
}

func CloseMongo() {
	if mongoClient != nil {
		ctx, cancel := context.WithTimeout(context.Background(), 5*time.Second)
		defer cancel()
		_ = mongoClient.Disconnect(ctx)
		log.Println("MongoDB connection closed")
	}
}