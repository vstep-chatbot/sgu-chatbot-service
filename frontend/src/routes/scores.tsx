import {Button} from "@/components/ui/button";
import {createFileRoute} from "@tanstack/react-router";

export const Route = createFileRoute("/scores")({
    component: ScoresComponent,
});

function ScoresComponent() {
    return (
        <div>
            <div className="flex justify-between items-center">
                <h3 className="text-2xl font-semibold">Danh sách điểm số</h3>
                <Button>Thêm file</Button>
            </div>
        </div>
    );
}
