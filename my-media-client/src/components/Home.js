import { useEffect, useState } from "react";
import { getMoviesPageMovieCount } from "../services/movieService";
import { SmallMovie } from "./Movie";

function Home() {
    const [moviePage, setMoviePage] = useState(1)
    const [movieCount, setMovieCount] = useState(6)
    const [movies, setMovies] = useState([]);
    const [errs, setErrs] = useState([]);


    useEffect(() => {
        getMoviesPageMovieCount(moviePage, movieCount)
            .then((page) => {
                console.log(page);
                setMovies(page.content);
            })
            .catch(setErrs);
    }, []);


    return (
        <>
            <h1 className="text-xl">Welcome to My Media!</h1>
            <h3 className="text-xl">Explore Movies</h3>
            <div id="smallMovies" className="grid grid-cols-3 bg-gray-100 p-3 m-2 rounded-lg">
            {
                    movies.map((m) => {
                        return <SmallMovie key={m.movieId} movie={m} />;
                    })
                }
            </div>
            <h3 className="text-md">Explore Tv Shows</h3>
        </>
    )
}

export default Home;