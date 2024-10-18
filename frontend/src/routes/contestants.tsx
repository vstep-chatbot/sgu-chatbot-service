import LoadingBlock from "@/components/atoms/LoadingBlock";
import RecordGroup from "@/components/molecules/RecordGroup";
import UploadFileDialog from "@/components/molecules/UploadFileDialog";
import useGetRecords from "@/services/useGetRecords";
import { SERVICE_TYPE } from "@/types/types";
import { createFileRoute } from "@tanstack/react-router";

export const Route = createFileRoute("/contestants")({
  component: DataPage,
});

export default function DataPage() {
  const { data: getRecordsDto, isLoading } = useGetRecords<"contestants">({
    recordType: "contestants",
  });

  return (
    <div>
      <div className="flex justify-between items-center">
        <h3 className="text-2xl font-semibold">Danh sách File thí sinh</h3>
        <UploadFileDialog type={"contestants"} />
      </div>
      {isLoading || getRecordsDto?.length === 0 ? (
        <LoadingBlock />
      ) : (
        getRecordsDto?.map((file) => (
          <RecordGroup
            type="contestants"
            key={file.filename}
            documents={file.records}
            filename={file.filename}
          />
        ))
      )}
    </div>
  );
}
