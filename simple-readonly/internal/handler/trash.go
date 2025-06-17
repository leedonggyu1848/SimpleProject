package handler

import (
	"context"
	"net/http"
	"simple-readonly/internal/service"
	"time"

	"github.com/gin-gonic/gin"
)

type TrashHandler struct {
	Service *service.TrashService
}

func NewTrashHandler(service *service.TrashService) *TrashHandler {
	return &TrashHandler{
		Service: service,
	}
}


func (h *TrashHandler) GetContentByID(c *gin.Context) {
    id := c.Param("id")
    ctx, cancel := context.WithTimeout(c.Request.Context(), 5*time.Second)
    defer cancel()

    content, err := h.Service.GetTrashByID(ctx, id)
    if err != nil {
        c.JSON(http.StatusNotFound, gin.H{"error": err.Error()})
        return
    }
    c.JSON(http.StatusOK, content)
}

func (h *TrashHandler) GetContentCount(c *gin.Context) {
    ctx, cancel := context.WithTimeout(c.Request.Context(), 5*time.Second)
    defer cancel()

    count, err := h.Service.GetTrashCount(ctx)
    if err != nil {
        c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
        return
    }
    c.JSON(http.StatusOK, gin.H{"count": count})
}