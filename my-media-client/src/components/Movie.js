
function Movie({ movie }) {
    return (
        <div className="bg-gray-100 p-10 my-2 rounded-lg shadow-md">
            <h1 className="text-xl font-bold">{movie.movieName}</h1>
            <div className="mt-2 mb-2">
                <p className="text-gray-600">Length: {movie.movieLength} Minutes</p>
                <p className="text-gray-600">Year: {movie.movieYear}</p>
                <p>Overview: {movie.movieOverview}</p>
            </div>
        </div>
    )
}

export default Movie;

export function SmallMovie({ movie }) {
    return (
        <div>
            <div className="m-2 text-gray-600 border">
                <p className="font-bold">{movie.movieName}</p>
                <p className="">Length: {movie.movieLength} Minutes</p>
                <p className="">Year: {movie.movieYear}</p>
                <p>Overview: {movie.movieOverview}</p>
            </div>
        </div>
    )
}

export function ListMovie({movie}) {
    return (
        <li>{movie.movieName}, Year: {movie.movieYear}, Length: {movie.movieLength}</li>
    )
}