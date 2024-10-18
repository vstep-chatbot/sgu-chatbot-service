import { createFileRoute } from "@tanstack/react-router";
import LoadingBlock from "@/components/atoms/LoadingBlock";
import DocumentGroup, {
  DocumentGroupBadge,
} from "@/components/molecules/DocumentGroup";
import UploadFileDialog from "@/components/molecules/UploadFileDialog";
import useGetRecords from "@/services/useGetRecords";
import { useMemo } from "react";

export const Route = createFileRoute("/scores")({
  component: ScoresPage,
});

export default function ScoresPage() {
  const { data: getRecordsDto, isLoading } = useGetRecords({
    recordType: "scores",
  });

  return (
    <div>
      <div className="flex justify-between items-center">
        <h3 className="text-2xl font-semibold">Danh sách File điểm</h3>
        <UploadFileDialog type="scores" />
      </div>
      {isLoading || getRecordsDto?.length === 0 ? (
        <LoadingBlock />
      ) : (
        getRecordsDto?.map((file) => (
          <DocumentGroup
            key={file._id}
            documents={file.records}
            filename={file._id}
            badges={[
              {
                label: "Ngày thi",
                value: file.competition_date
                  ? new Date(file.competition_date).toLocaleDateString(
                      "en-GB",
                      {
                        dateStyle: "short",
                      }
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
