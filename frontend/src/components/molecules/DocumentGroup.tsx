import { GetRecordsDto } from "@/types/types";
import Document from "../atoms/Document";
import {
  Accordion,
  AccordionContent,
  AccordionItem,
  AccordionTrigger,
} from "@/components/ui/accordion";
import { useState } from "react";
import AddMoreCard from "../atoms/AddMoreCard";
import { Badge } from "../ui/badge";
import { FileText } from "lucide-react";

export type DocumentGroupBadge = {
  label: string | number;
  value: string | number;
};

type DocumentGroupProps = {
  documents: GetRecordsDto["records"];
  filename: string;
  badges?: DocumentGroupBadge[];
};

export default function DocumentGroup({
  documents,
  filename,
  badges,
}: DocumentGroupProps) {
  const [length, setLength] = useState(10);

  return (
    <Accordion type="single" collapsible>
      <AccordionItem value="item-1">
        <AccordionTrigger>
          <div className="flex gap-5">
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
          </div>
        </AccordionTrigger>
        <AccordionContent className="flex gap-4 flex-wrap">
          {documents.slice(0, length).map((document) => (
            <Document key={document["_id"]} document={document} />
          ))}
          <AddMoreCard onClick={() => setLength(length + 10)} />
        </AccordionContent>
      </AccordionItem>
    </Accordion>
  );
}
