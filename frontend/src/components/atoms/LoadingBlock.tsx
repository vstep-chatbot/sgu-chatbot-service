export default function LoadingBlock() {
  return (
    <div className="animate-pulse rounded-lg bg-gray-200 p-4">
      <div className="mb-4 h-4 w-1/2 bg-gray-300"></div>
      <div className="mb-4 h-4 w-1/3 bg-gray-300"></div>
      <div className="mb-4 h-4 w-3/4 bg-gray-300"></div>
      <div className="mb-4 h-4 w-1/2 bg-gray-300"></div>
      <div className="mb-4 h-4 w-1/3 bg-gray-300"></div>
      <div className="mb-4 h-4 w-3/4 bg-gray-300"></div>
    </div>
  );
}
