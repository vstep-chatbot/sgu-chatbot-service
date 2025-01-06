import { GetContestantsDto, SERVICE_TYPE } from "@/types/types";
import Record from "../atoms/Record";
import {
  Accordion,
  AccordionContent,
  AccordionItem,
  AccordionTrigger,
} from "@/components/ui/accordion";
import { MouseEventHandler, useState } from "react";
import AddMoreCard from "../atoms/AddMoreCard";
import { Badge } from "../ui/badge";
import { FileText, Trash2 } from "lucide-react";
import useDeleteFile from "@/services/useDeleteFile";

export type DocumentGroupBadge = {
  label: string | number;
  value: string | number;
};

type DocumentGroupProps = {
  documents: GetContestantsDto["records"];
  filename: string;
  type: SERVICE_TYPE;
  badges?: DocumentGroupBadge[];
};

export default function RecordGroup({
  documents,
  filename,
  type,
  badges,
}: DocumentGroupProps) {
  const [length, setLength] = useState(10);

  const { mutate: deleteFile } = useDeleteFile();

  const handleDelete: MouseEventHandler<HTMLDivElement> = (e) => {
    e.stopPropagation();
    console.log("Deleting file", filename);
    deleteFile({ filename, type });
  };

  return (
    <Accordion type="single" collapsible>
      <AccordionItem value="item-1">
        <AccordionTrigger>
          <div className="flex w-full gap-5 pr-4">
            <FileText size={24} />
            <p>{filename}</p>
            {badges?.map((badge, index) => (
              <Badge
                variant="secondary"
                key={JSON.stringify({ ...badge, index })}
              >
                <span className="mr-1">{badge.label}</span>
                <span className="font-normal">{badge.value}</span>
              </Badge>
            ))}
            <div className="grow" />
            <Badge variant="destructive" onClick={handleDelete}>
              <span>
                <Trash2 size={14} className="mr-1" />
              </span>
              <span>Xoá</span>
            </Badge>
          </div>
        </AccordionTrigger>
        <AccordionContent className="flex flex-wrap gap-4">
          {documents.slice(0, length).map((document) => (
            <Record key={document._id} document={document} />
          ))}
          <AddMoreCard onClick={() => setLength(length + 10)} />
        </AccordionContent>
      </AccordionItem>
    </Accordion>
  );
}
