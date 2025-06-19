package router

import (
	"simple-readonly/internal/handler"

	"github.com/gin-gonic/gin"
)

func SetupRouter(trashHandler *handler.TrashHandler) *gin.Engine {
    r := gin.Default()
    r.Use(CORSMiddleware())
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

func CORSMiddleware() gin.HandlerFunc {
    return func(c *gin.Context) {
        c.Writer.Header().Set("Access-Control-Allow-Origin", "*")
        c.Writer.Header().Set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
        c.Writer.Header().Set("Access-Control-Allow-Headers", "Origin, Content-Type, Authorization")

        if c.Request.Method == "OPTIONS" {
            c.AbortWithStatus(204)
            return
        }
        c.Next()
    }
}