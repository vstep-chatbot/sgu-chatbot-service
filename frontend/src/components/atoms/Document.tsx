import {GetRecordsDto} from "@/types/types";
import {Card, CardContent,} from "@/components/ui/card";

type DocumentProps = {
    document: GetRecordsDto["records"][0];
};

export default function Document({document}: DocumentProps) {
    return (
        <Card className="max-w-[450px]">
            {/* <CardHeader>
        <CardTitle>Card Title</CardTitle>
        <CardDescription>Card Description</CardDescription>
      </CardHeader> */}
            <CardContent className="pt-6 space-y-1">
                {/* <pre>
          <code>{JSON.stringify(document.data, null, 2)}</code>
        </pre> */}
                {Object.entries(document.data).map(([key, value]) => (
                    <p>
                        <span className="font-medium">- {key}: </span>
                        <span className="text-green-600">{value}</span>
                    </p>
                ))}
            </CardContent>
            {/* <CardFooter>
        <p>Card Footer</p>
      </CardFooter> */}
        </Card>
    );
}
