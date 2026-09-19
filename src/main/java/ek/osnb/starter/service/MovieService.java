package ek.osnb.starter.service;

import ek.osnb.starter.exceptions.NotFoundException;
import ek.osnb.starter.model.Actor;
import ek.osnb.starter.model.Movie;
import ek.osnb.starter.repository.ActorRepository;
import ek.osnb.starter.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;

    public MovieService(MovieRepository movieRepository, ActorRepository actorRepository) {
        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
    }

    public Movie createMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(Long id) {
        Optional<Movie> movieOptional = movieRepository.findById(id);
        if (movieOptional.isEmpty()) {
            throw new NotFoundException("Movie not found with id: " + id);
        }
        // Or use shortcut:
        // return movieRepository.findById(id).orElseThrow(() -> new NotFoundException("Movie not found with id: " + id));

        return movieOptional.get();
    }

    public Movie addActorToMovie(Long movieId, Long actorid) {

            Optional<Movie> movie = movieRepository.findById(movieId);
            if (movie.isEmpty()){
                throw new NotFoundException ("Movie not found");
        }
            Optional<Actor> actor = actorRepository.findById(actorid);
            movie.get().getActors().add(actor.get());

            if (actor.isEmpty())
                throw new NotFoundException("actor not found");

            movieRepository.save(movie.get());
            return movie.get();

    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }
}