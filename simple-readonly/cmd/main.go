package main

import (
	"context"
	"log"
	config "simple-readonly/internal/configuration"
	"simple-readonly/internal/handler"
	"simple-readonly/internal/model"
	"simple-readonly/internal/repository"
	"simple-readonly/internal/router"
	"simple-readonly/internal/service"
	"simple-readonly/internal/utils"
	"time"

	"github.com/segmentio/kafka-go"
)

func main() {
    // mongoDB connection setup
    mongoURL := utils.GetEnv("MONGO_URL", "mongodb://root:example@localhost:27017/?authSource=admin")
    mongoDBName := utils.GetEnv("MONGO_DATABASE", "testdb")
    mongodb := config.GetMongoClient(mongoURL).Database(mongoDBName)
    defer config.CloseMongo()

    // Trash domain setup
    trashRepo, err := repository.NewMongoTrashRepository(mongodb)
    if err != nil {
        log.Fatalf("Failed to create trash repository: %s", err.Error())
    }
    trashSvc := service.NewTrashService(trashRepo)

    // Kafka consumer setup
    mqTrashConfig := config.KafkaConfig{
        Brokers:    []string{utils.GetEnv("KAFKA_URL", "localhost:9095")},
        Topic:      "trash",
        GroupID:    utils.GetEnv("KAFKA_GROUP_ID", "trash-group"),
    }

    config.StartKafikaConsumer(mqTrashConfig, func(msg kafka.Message) {
        ctx, cancel := context.WithTimeout(context.Background(), 5*time.Second)
        defer cancel()
        trash, err := model.FromJson(msg.Value)
        if err != nil {
            log.Printf("Failed to parse message: %s", msg.Value)
            return
        }
        err = trashRepo.Save(ctx, trash)
        if err != nil {
            log.Printf("Failed to save trash: %s with %s", err.Error(), msg.Value)
            return
        }
    })

    // Router setup
    h := handler.NewTrashHandler(trashSvc)
    router.SetupRouter(h).Run(":8000")
}