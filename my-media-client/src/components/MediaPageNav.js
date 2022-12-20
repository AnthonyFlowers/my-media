import { useState } from "react";
import { useSearchParams } from "react-router-dom";

function MediaPageNav({ pages }) {

    const [searchParams, setSearchParams] = useSearchParams();
    const [pageInput, setPageInput] = useState(pages.current);
    const [timeoutId, setTimeoutId] = useState();

    function handleMoviePage(evt) {
        const nextPage = evt.target.value;
        if (nextPage > pages.end || nextPage < pages.start) {
            return;
        }
        searchParams.set("page", nextPage);
        setPageInput(nextPage);
        setSearchParams(searchParams);
    }

    function handleChange(evt) {
        clearTimeout(timeoutId);
        const nextValue = evt.target.value;
        if (nextValue > pages.end) {
            setPageInput(pages.end);
        } else if (nextValue < pages.start) {
            setPageInput(pages.start);
        } else {
            setPageInput(evt.target.value);
        }
        const t = setTimeout(() => {
            searchParams.set("page", nextValue);
            setSearchParams(searchParams);
        }, 2000);
        setTimeoutId(t);
    }
    return (
        <ul className="flex justify-center my-2 gap-4">
            <li>
                {pages.start < pages.current ?
                    <button value={pages.start}
                        onClick={handleMoviePage}>
                        {pages.start}
                    </button>
                    : "1"}
            </li>

            <li><button value={pages.current - 1} onClick={handleMoviePage}>&lt;--</button></li>

            <li>
                <input
                    className="w-2rem text-center"
                    type="number" min={pages.start} max={pages.end}
                    placeholder={pages.current} value={pageInput}
                    onChange={handleChange}
                />
            </li>

            <li><button value={pages.current + 1} onClick={handleMoviePage}>--&gt;</button></li>

            <li>
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

export default MediaPageNav;