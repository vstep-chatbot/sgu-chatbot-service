import {MyRecord} from "@/types/types";
import Document from "../atoms/Document";
import {
    Accordion,
    AccordionContent,
    AccordionItem,
    AccordionTrigger,
} from "@/components/ui/accordion";
import {useState} from "react";
import AddMoreCard from "../atoms/AddMoreCard";

type DocumentGroupProps = {
    documents: MyRecord[];
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
                <AccordionTrigger>{filename}</AccordionTrigger>
                <AccordionContent className="flex gap-4">
                    {documents.slice(0, length).map((document) => (
                        <Document key={document.id} document={document}/>
                    ))}
                    <AddMoreCard onClick={() => setLength(length + 10)}/>
                </AccordionContent>
            </AccordionItem>
        </Accordion>
    );
}
