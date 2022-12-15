package mymedia.controllers;

import mymedia.domain.TvShowService;
import mymedia.models.Movie;
import mymedia.models.TvShow;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/tv-show")
public class TvShowController {

    private final TvShowService service;

    public TvShowController(TvShowService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getTvShows(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "50") int pageSize) {
        Page<TvShow> tvShows = service.findTvShows(page, pageSize);
        return new ResponseEntity<>(tvShows, HttpStatus.OK);
    }
}
