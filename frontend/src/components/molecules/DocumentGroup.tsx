import {GetRecordsDto} from "@/types/types";
import Document from "../atoms/Document";
import {Accordion, AccordionContent, AccordionItem, AccordionTrigger,} from "@/components/ui/accordion";
import {useState} from "react";
import AddMoreCard from "../atoms/AddMoreCard";
import {Badge} from "../ui/badge";
import {FileText} from "lucide-react";

type DocumentGroupProps = {
    documents: GetRecordsDto["records"];
    filename: string;
};

export default function DocumentGroup({
                                          documents,
                                          filename,
                                      }: DocumentGroupProps) {
    const [length, setLength] = useState(10);

    return (
        <Accordion type="single" collapsible>
            <AccordionItem value="item-1">
                <AccordionTrigger>
                    <div className="flex gap-5">
                        <FileText size={24}/>
                        <p>{filename}</p>
                        <Badge variant="secondary">
                            <span className="mr-1">{documents.length}</span>
                            <span className="font-normal">thí sinh</span>
                        </Badge>
                    </div>
                </AccordionTrigger>
                <AccordionContent className="flex gap-4 flex-wrap">
                    {documents.slice(0, length).map((document) => (
                        <Document key={document["_id"]} document={document}/>
                    ))}
                    <AddMoreCard onClick={() => setLength(length + 10)}/>
                </AccordionContent>
            </AccordionItem>
        </Accordion>
    );
}
