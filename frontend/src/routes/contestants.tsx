import {createFileRoute} from "@tanstack/react-router";
import DataPage from "@/components/layouts/DataPage";

export const Route = createFileRoute("/contestants")({
    component: () => (
        <DataPage
            recordType="contestants"
            title="Danh sách File thông tin thí sinh"
        />
    ),
});
