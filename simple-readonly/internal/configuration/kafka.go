package config

import (
	"context"
	"log"

	"github.com/segmentio/kafka-go"
)

type KafkaConfig struct {
	Brokers []string
	Topic   string
	GroupID string
}

func StartKafikaConsumer(config KafkaConfig, handler func(msg kafka.Message)) {
	r := kafka.NewReader(kafka.ReaderConfig{
		  Brokers:		config.Brokers,
		  GroupID:		config.GroupID,
		  Topic:		config.Topic,
		  MinBytes: 	10e3, // 10KB
		  MaxBytes:		10e6, // 10MB
	})
	defer r.Close()
	log.Printf("Kafka consumer started for topic: %s", config.Topic)
	for {
		msg, err := r.ReadMessage(context.Background())
		if err != nil {
			   log.Printf("Error reading message: %v", err)
			   continue
		}
		go handler(msg)
  }
}