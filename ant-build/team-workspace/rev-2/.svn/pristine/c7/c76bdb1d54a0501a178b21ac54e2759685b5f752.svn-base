package org.example.controller;

import org.example.entity.PointEntity;
import org.example.exception.ExceptionHandler;
import org.example.repository.ResultsRepository;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collections;
import java.util.List;

@Named("resultsController")
@RequestScoped
public class ResultsController {

    @Inject
    private ResultsRepository resultsRepository;

    @Inject
    private ExceptionHandler exceptionHandler;

    private List<PointEntity> results = Collections.emptyList();

    @PostConstruct
    public void init() {
        try {
            loadResults();
        } catch (Exception e) {
            exceptionHandler.handleAndAddToContext(e);
            results = Collections.emptyList();
        }
    }

    public void loadResults() {
        try {
            results = resultsRepository.findAll();
        } catch (Exception e) {
            exceptionHandler.handleAndAddToContext(e);
            results = Collections.emptyList();
        }
    }

    public List<PointEntity> getAll() {
        return results;
    }

    public void clear() {
        try {
            resultsRepository.clear();
            loadResults();
        } catch (Exception e) {
            exceptionHandler.handleAndAddToContext(e);
        }
    }

    public int getCount() {
        return results.size();
    }
}
