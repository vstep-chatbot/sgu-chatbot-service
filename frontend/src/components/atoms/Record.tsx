import { GetContestantsDto } from "@/types/types";
import { Card, CardContent } from "@/components/ui/card";

type DocumentProps = {
  document: GetContestantsDto["records"][0];
};

export default function Record({ document }: DocumentProps) {
  return (
    <Card className="max-w-[450px]">
      <CardContent className="space-y-1 pt-6">
        {Object.entries(document.data).map(([key, value]) => (
          <p key={key}>
            <span className="font-medium">- {key}: </span>
            <span className="text-green-600">{value}</span>
          </p>
        ))}
      </CardContent>
    </Card>
  );
}
