package repository

import (
	"context"
	"errors"
	"log"
	"simple-readonly/internal/model"

	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/bson/primitive"
	"go.mongodb.org/mongo-driver/mongo"
)

type TrashRepository interface {
    FindByID(ctx context.Context, id string) (*model.Trash, error)
    CountAll(ctx context.Context) (int64, error)
}

type mongoTrashRepository struct {
    collection *mongo.Collection
}

const (
    collectionName = "trashRead"
)

func NewMongoTrashRepository(db *mongo.Database) (*mongoTrashRepository, error) {
    c := db.Collection(collectionName)
    if c == nil {
        return nil, errors.New("failed to get collection: " + collectionName)
    }
    return &mongoTrashRepository{collection: c}, nil
}

func (r *mongoTrashRepository) Save(ctx context.Context, content *model.Trash) error {
    log.Printf("save content: %d, %s", content.ID, content.Content)
    _, err := r.collection.InsertOne(ctx, content)
    return err
}

func (r *mongoTrashRepository) FindByID(ctx context.Context, id string) (*model.Trash, error) {
    objID, err := primitive.ObjectIDFromHex(id)
    if err != nil {
        return nil, errors.New("invalid ID format")
    }
    var content model.Trash
    err = r.collection.FindOne(ctx, bson.M{"_id": objID}).Decode(&content)
    if err == mongo.ErrNoDocuments {
        return nil, errors.New("content not found")
    }
    return &content, err
}

func (r *mongoTrashRepository) CountAll(ctx context.Context) (int64, error) {
    return r.collection.CountDocuments(ctx, bson.M{})
}