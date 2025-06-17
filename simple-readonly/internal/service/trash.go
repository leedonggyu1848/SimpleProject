package service

import (
	"context"
	"simple-readonly/internal/model"
	"simple-readonly/internal/repository"
)

type TrashService struct {
	repo repository.TrashRepository
}

func NewTrashService(repo repository.TrashRepository) *TrashService {
	return &TrashService{repo: repo}
}

func (s *TrashService) GetTrashByID(ctx context.Context, id string) (*model.Trash, error) {
	return s.repo.FindByID(ctx, id)
}

func (s *TrashService) GetTrashCount(ctx context.Context) (int64, error) {
	return s.repo.CountAll(ctx)
}