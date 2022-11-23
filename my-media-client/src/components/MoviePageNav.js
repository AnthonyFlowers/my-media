import { useEffect } from "react";
import { useSearchParams } from "react-router-dom";

function MoviePageNav({ pages, setParams }) {
    const [searchParams, setSearchParams] = useSearchParams();

    useEffect(() => {
        console.log(pages);
    }, []);

    function handleMoviePage(evt) {
        if (evt.target.value > pages.end || evt.target.value < pages.start) {
            return;
        }
        setParams({
            page: evt.target.value
        });
    }
    return (
        <ul className="flex justify-center my-2 grid-rows-3 gap-4">
            <li className="row-span-1">
                {pages.start < pages.current ?
                    <button value={pages.start}
                        onClick={handleMoviePage}>
                        {pages.start}
                    </button>
                    : "1"}
            </li>

            <li><button value={pages.current - 1} onClick={handleMoviePage}>&lt;--</button></li>

            <li className="row-span-1">{pages.current}</li>

            <li><button value={pages.current + 1} onClick={handleMoviePage}>--&gt;</button></li>

            <li className="row-span-1">
                {pages.end >= pages.current ?
                    <button value={pages.end}
                        onClick={handleMoviePage}>
                        {pages.end}
                    </button>
                    : pages.end}
            </li>
        </ul>
    )
}

export default MoviePageNav;