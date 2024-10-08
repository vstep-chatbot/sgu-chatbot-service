import {MyRecord} from "@/types/types";
import {
    Card,
    CardContent,
    CardDescription,
    CardFooter,
    CardHeader,
    CardTitle,
} from "@/components/ui/card";

type DocumentProps = {
    document: MyRecord;
};

export default function Document({document}: DocumentProps) {
    return (
        <Card>
            {/* <CardHeader>
        <CardTitle>Card Title</CardTitle>
        <CardDescription>Card Description</CardDescription>
      </CardHeader> */}
            <CardContent>
        <pre>
          <code>{JSON.stringify(document.data, null, 2)}</code>
        </pre>
            </CardContent>
            {/* <CardFooter>
        <p>Card Footer</p>
      </CardFooter> */}
        </Card>
    );
}
