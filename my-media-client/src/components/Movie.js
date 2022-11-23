
export default function Movie({ movie }) {
    return (
        <div className="bg-white p-10 my-2 rounded-lg shadow-md">
            <h1 className="text-xl font-bold">{movie.movieName}</h1>
            <div className="mt-2 mb-2">
                <p className="text-gray-600">Length: {movie.movieLength} Minutes</p>
                <p className="text-gray-600">Year: {movie.movieYear}</p>
                <p>{movie.movieOverview}</p>
            </div>
        </div>
    )
}