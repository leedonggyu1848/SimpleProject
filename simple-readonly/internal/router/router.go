package router

import (
	"simple-readonly/internal/handler"

	"github.com/gin-gonic/gin"
)

func SetupRouter(trashHandler *handler.TrashHandler) *gin.Engine {
    r := gin.Default()
    test := r.Group("/test")
    {
        test.GET("/ping", handler.PingHandler)
    }

    trash := r.Group("/trash")
    {
        trash.GET("/:id", trashHandler.GetContentByID)
        trash.GET("/count", trashHandler.GetContentCount)
    }

    return r
}