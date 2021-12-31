package com.example.spring.demo.service;

import org.springframework.stereotype.Service;

import com.example.spring.demo.repository.BoardRepository;
import com.example.spring.demo.vo.Board;

@Service
public class BoardService {
	private BoardRepository boardRepository;

	public BoardService(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}

	public Board getBoardById(int id) {
		return boardRepository.getBoardById(id);
	}
}
