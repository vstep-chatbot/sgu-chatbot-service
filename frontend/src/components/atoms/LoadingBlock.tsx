export default function LoadingBlock() {
    return (
        <div className="animate-pulse bg-gray-200 rounded-lg p-4">
            <div className="h-4 bg-gray-300 w-1/2 mb-4"></div>
            <div className="h-4 bg-gray-300 w-1/3 mb-4"></div>
            <div className="h-4 bg-gray-300 w-3/4 mb-4"></div>
            <div className="h-4 bg-gray-300 w-1/2 mb-4"></div>
            <div className="h-4 bg-gray-300 w-1/3 mb-4"></div>
            <div className="h-4 bg-gray-300 w-3/4 mb-4"></div>
        </div>
    );
}
