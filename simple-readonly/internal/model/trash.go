package model

import "encoding/json"

type Trash struct {
	ID		int64  `json:"id"`
	Content string `json:"content"`
}

func FromJson(data []byte) (*Trash, error) {
	var trash Trash
	err := json.Unmarshal(data, &trash)
	if err != nil {
		return nil, err
	}
	return &trash, nil
}