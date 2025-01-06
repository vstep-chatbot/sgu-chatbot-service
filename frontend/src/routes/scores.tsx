import { createFileRoute } from "@tanstack/react-router";
import LoadingBlock from "@/components/atoms/LoadingBlock";
import RecordGroup from "@/components/molecules/RecordGroup";
import UploadFileDialog from "@/components/molecules/UploadFileDialog";
import useGetRecords from "@/services/useGetRecords";

export const Route = createFileRoute("/scores")({
  component: ScoresPage,
});

export default function ScoresPage() {
  const { data: getRecordsDto, isLoading } = useGetRecords<"scores">({
    recordType: "scores",
  });

  return (
    <div>
      <div className="flex items-center justify-between">
        <h3 className="text-2xl font-semibold">Danh sách File điểm</h3>
        <UploadFileDialog type="scores" />
      </div>
      {isLoading || getRecordsDto?.length === 0 ? (
        <LoadingBlock />
      ) : (
        getRecordsDto?.map((file) => (
          <RecordGroup
            type="scores"
            key={file._id.filename}
            documents={file.records}
            filename={file._id.filename}
            badges={[
              {
                label: "Ngày thi",
                value: file._id.filename
                  ? new Date(file._id.competition_date).toLocaleDateString(
                      "en-GB",
                      {
                        dateStyle: "short",
                      },
                    )
                  : "Chưa cập nhật",
              },
              {
                label: file.count,
                value: "thí sinh",
              },
            ]}
          />
        ))
      )}
    </div>
  );
}
