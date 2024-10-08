import UploadFileDialog from "@/components/molecules/UploadFileDialog";
import {Button} from "@/components/ui/button";
import {createFileRoute} from "@tanstack/react-router";

export const Route = createFileRoute("/contestants")({
    component: ContestantsComponent,
});

function ContestantsComponent() {
    return (
        <div>
            <div className="flex justify-between items-center">
                <h3 className="text-2xl font-semibold">Danh sách thí sinh</h3>
                <UploadFileDialog/>
            </div>
        </div>
    );
}
