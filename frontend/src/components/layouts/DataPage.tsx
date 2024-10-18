import LoadingBlock from "@/components/atoms/LoadingBlock";
import DocumentGroup from "@/components/molecules/DocumentGroup";
import UploadFileDialog from "@/components/molecules/UploadFileDialog";
import useGetRecords from "@/services/useGetRecords";

type DataPageProps = {
  title: string;
  recordType: "contestants" | "scores";
};

export default function DataPage({ title, recordType }: DataPageProps) {
  const { data: getRecordsDto, isLoading } = useGetRecords({
    recordType,
  });

  return (
    <div>
      <div className="flex justify-between items-center">
        <h3 className="text-2xl font-semibold">{title}</h3>
        <UploadFileDialog type={recordType} />
      </div>
      {isLoading || getRecordsDto?.length === 0 ? (
        <LoadingBlock />
      ) : (
        getRecordsDto?.map((file) => (
          <DocumentGroup
            key={file._id}
            documents={file.records}
            filename={file._id}
          />
        ))
      )}
    </div>
  );
}
