import {MyRecord} from "@/types/types";
import {
    Card,
    CardContent,
    CardDescription,
    CardFooter,
    CardHeader,
    CardTitle,
} from "@/components/ui/card";
import {Ellipsis} from "lucide-react";

type AddMoreProps = {
    onClick: () => void;
};

export default function AddMoreCard({onClick}: AddMoreProps) {
    return (
        <Card>
            <CardContent onClick={onClick}>
                <Ellipsis size={64}/>
            </CardContent>
            {/* <CardFooter>
        <p>Card Footer</p>
      </CardFooter> */}
        </Card>
    );
}
